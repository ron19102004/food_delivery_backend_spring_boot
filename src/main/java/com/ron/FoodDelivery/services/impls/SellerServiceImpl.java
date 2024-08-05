package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.aws.AwsConfiguration;
import com.ron.FoodDelivery.aws.AwsS3Service;
import com.ron.FoodDelivery.entities.location.LocationEntity;
import com.ron.FoodDelivery.entities.request_role_account.dto.RequestCreateRequestRoleAccDataDto;
import com.ron.FoodDelivery.entities.seller.SellerEntity;
import com.ron.FoodDelivery.entities.seller.dto.RequestUpdateInformationSellerDto;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.exceptions.ServiceException;
import com.ron.FoodDelivery.repositories.LocationRepository;
import com.ron.FoodDelivery.repositories.SellerRepository;
import com.ron.FoodDelivery.services.SellerService;
import com.ron.FoodDelivery.utils.RegexValid;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SellerServiceImpl implements SellerService {
    @Value("${constant.images.default.seller-bg}")
    private String SELLER_BACKGROUND;
    @Value("${constant.images.default.avatar}")
    private String AVATAR_DEFAULT_URL;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private LocationRepository locationRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private AwsS3Service awsS3Service;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    @Autowired
    private RegexValid regexValid;

    @Override
    public void init_account(UserEntity user, RequestCreateRequestRoleAccDataDto requestCreateRequestRoleAccDataDto) {
        SellerEntity sellerEntity = SellerEntity.builder()
                .email(requestCreateRequestRoleAccDataDto.email())
                .phone_number(requestCreateRequestRoleAccDataDto.phone_number())
                .enabled(false)
                .name(requestCreateRequestRoleAccDataDto.name())
                .user(user)
                .address("Waiting update...")
                .background_image(SELLER_BACKGROUND)
                .avatar(AVATAR_DEFAULT_URL)
                .build();
        sellerRepository.save(sellerEntity);
    }

    @Transactional
    @Override
    public void update_information(String username, RequestUpdateInformationSellerDto requestUpdateInformationSellerDto) {
        SellerEntity seller = sellerRepository.findByUsernameAndEnabled(username,true);
        if (seller == null) throw new ServiceException("Seller not found!", HttpStatus.NOT_FOUND);
        LocationEntity location = locationRepository.findById(requestUpdateInformationSellerDto.location_id()).orElse(null);
        if (location == null) throw new ServiceException("Location not found!", HttpStatus.NOT_FOUND);
        seller.setName(requestUpdateInformationSellerDto.name());
        seller.setAddress(requestUpdateInformationSellerDto.address());
        seller.setEmail(requestUpdateInformationSellerDto.email());
        seller.setClose_at(requestUpdateInformationSellerDto.close_at());
        seller.setOpen_at(requestUpdateInformationSellerDto.open_at());
        seller.setPhone_number(requestUpdateInformationSellerDto.phone_number());
        seller.setLatitude(requestUpdateInformationSellerDto.latitude());
        seller.setLongitude(requestUpdateInformationSellerDto.longitude());
        seller.setLocation(location);
        entityManager.merge(seller);
    }

    @Transactional
    @Override
    public String update_avatar(String username, MultipartFile image) {
        SellerEntity seller = sellerRepository.findByUsernameAndEnabled(username,true);
        if (seller == null) throw new ServiceException("Seller not found!", HttpStatus.NOT_FOUND);
        String urlOld = seller.getAvatar();
        String url = awsS3Service.upload(image, AwsConfiguration.AVATAR_FOLDER);
        seller.setAvatar(url);
        entityManager.merge(seller);
        if (regexValid.isAwsS3Url(urlOld)){
            executorService.submit(() -> {
                awsS3Service.delete(urlOld,AwsConfiguration.AVATAR_FOLDER);
            });
        }
        return url;
    }

    @Transactional
    @Override
    public void set_enable_account(Long id, Boolean enable) {
        SellerEntity sellerEntity = sellerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Seller account not found"));
        sellerEntity.setEnabled(enable);
        entityManager.merge(sellerEntity);
    }
}
