package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.entities.category.CategoryEntity;
import com.ron.FoodDelivery.entities.seller.SellerEntity;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.entities.user.UserRole;
import com.ron.FoodDelivery.entities.voucher.VoucherEntity;
import com.ron.FoodDelivery.entities.voucher.dto.RequestCreateVoucherDto;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.exceptions.ServiceException;
import com.ron.FoodDelivery.repositories.CategoryRepository;
import com.ron.FoodDelivery.repositories.SellerRepository;
import com.ron.FoodDelivery.repositories.UserRepository;
import com.ron.FoodDelivery.repositories.VoucherRepository;
import com.ron.FoodDelivery.services.VoucherService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

    private void checkCreateVoucherDto(StringBuilder errStr, RequestCreateVoucherDto dto) {
        if (dto.percent() < 0 || dto.percent() > 100) errStr.append("Percent must be between 0 and 100.");
        if (dto.quantity() < 0) errStr.append("Quantity must larger than 0.");
        if (dto.issued_at().before(new Date()) || dto.expired_at().before(new Date()))
            errStr.append("Issued at or expired at must after today.");
        if (dto.expired_at().before(dto.issued_at()))
            errStr.append("Issued at must before expired at.");
    }

    private VoucherEntity create(RequestCreateVoucherDto dto, SellerEntity seller, boolean isSystem) {
        CategoryEntity category = categoryRepository.findByIdAndDeleted(dto.category_id(), false);
        if (category == null) throw new EntityNotFoundException("Category not found");
        return VoucherEntity.builder()
                .name(dto.name())
                .hidden(false)
                .expired_at(dto.expired_at())
                .issued_at(dto.issued_at())
                .percent(dto.percent())
                .quantity(dto.quantity())
                .category(category)
                .seller(seller)
                .quantity_current(dto.quantity())
                .code(dto.code() + "$" + System.currentTimeMillis())
                .of_system(isSystem)
                .build();
    }

    @Override
    public VoucherEntity create(String username, RequestCreateVoucherDto dto) {
        StringBuilder errStr = new StringBuilder();
        checkCreateVoucherDto(errStr, dto);
        if (!errStr.isEmpty())
            throw new ServiceException(errStr.toString(), HttpStatus.BAD_REQUEST);
        SellerEntity seller = sellerRepository.findByUsernameAndEnabled(username, true);
        if (seller == null) throw new EntityNotFoundException("Seller not found");
        VoucherEntity voucher = this.create(dto, seller, false);
        return voucherRepository.save(voucher);
    }

    @Override
    public VoucherEntity createWithSystem(RequestCreateVoucherDto dto) {
        StringBuilder errStr = new StringBuilder();
        checkCreateVoucherDto(errStr, dto);
        if (!errStr.isEmpty())
            throw new ServiceException(errStr.toString(), HttpStatus.BAD_REQUEST);
        VoucherEntity voucher = this.create(dto, null, true);
        return voucherRepository.save(voucher);
    }

    @Transactional
    @Override
    public void changeHidden(String username, Long voucherId) {
        VoucherEntity voucher = voucherRepository.findByIdAndHidden(voucherId, false);
        if (voucher == null) throw new EntityNotFoundException("Voucher not found");
        if (voucher.getOf_system()) {
            UserEntity user = userRepository.findByUsernameAndIsLocked(username, false)
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            if (!user.getRole().equals(UserRole.ADMIN))
                throw new ServiceException("Not permission", HttpStatus.FORBIDDEN);
            voucher.setHidden(!voucher.getHidden());
            entityManager.merge(voucher);
            return;
        }
        SellerEntity seller = sellerRepository.findByUsernameAndEnabled(username, true);
        if (seller == null) throw new EntityNotFoundException("Seller not found");
        if (!seller.getId().equals(voucher.getSeller().getId()))
            throw new ServiceException("Not permission", HttpStatus.FORBIDDEN);
        voucher.setHidden(!voucher.getHidden());
        entityManager.merge(voucher);
    }

    @Override
    public List<VoucherEntity> findAllBySellerUsername(String seller_username) {
        SellerEntity seller = sellerRepository.findByUsernameAndEnabled(seller_username, true);
        if (seller == null) throw new EntityNotFoundException("Seller not found");
        return voucherRepository.findAllBySellerUsername(seller_username,false);
    }

    @Override
    public List<VoucherEntity> findAllOfSystem() {
        return voucherRepository.findAllOfSystem(false);
    }

    @Override
    public List<VoucherEntity> findAllBySellerId(Long id) {
        SellerEntity seller = sellerRepository.findByIdAndEnabled(id, true);
        if (seller == null) throw new EntityNotFoundException("Seller not found");
        return voucherRepository.findAllBySellerId(id,false);
    }
}
