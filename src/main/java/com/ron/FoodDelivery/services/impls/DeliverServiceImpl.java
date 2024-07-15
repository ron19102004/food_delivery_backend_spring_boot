package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.aws.AwsConfiguration;
import com.ron.FoodDelivery.aws.AwsS3Service;
import com.ron.FoodDelivery.entities.deliver.DeliverEntity;
import com.ron.FoodDelivery.entities.deliver.dto.RequestUpdateInformationDeliverDto;
import com.ron.FoodDelivery.entities.request_role_account.dto.RequestCreateRequestRoleAccDataDto;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.exceptions.ServiceException;
import com.ron.FoodDelivery.repositories.DeliveryRepository;
import com.ron.FoodDelivery.services.DeliverService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DeliverServiceImpl implements DeliverService {
    @Autowired
    private DeliveryRepository deliveryRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private AwsS3Service awsS3Service;

    @Transactional
    @Override
    public void set_enable_account(Long id, Boolean enable) {
        DeliverEntity deliverEntity = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery account not found"));
        deliverEntity.setEnabled(enable);
        entityManager.merge(deliverEntity);
    }

    @Transactional
    @Override
    public void update_information(String username, RequestUpdateInformationDeliverDto requestUpdateInformationDeliverDto) {
        DeliverEntity deliver = deliveryRepository.findByUsername(username);
        if (deliver == null) throw new ServiceException("Deliver not found!", HttpStatus.NOT_FOUND);
        deliver.setPhone_number(requestUpdateInformationDeliverDto.phone_number());
        deliver.setEmail(requestUpdateInformationDeliverDto.email());
        deliver.setName(requestUpdateInformationDeliverDto.name());
        entityManager.merge(deliver);
    }
    @Transactional
    @Override
    public String update_avatar(String username, MultipartFile image) {
        DeliverEntity deliver = deliveryRepository.findByUsername(username);
        if (deliver == null) throw new ServiceException("Deliver not found!", HttpStatus.NOT_FOUND);
        String url = awsS3Service.upload(image, AwsConfiguration.AVATAR_FOLDER);
        deliver.setAvatar(url);
        entityManager.merge(deliver);
        return url;
    }


    @Override
    public void init_account(UserEntity user, RequestCreateRequestRoleAccDataDto requestCreateRequestRoleAccDataDto) {
        DeliverEntity sellerEntity = DeliverEntity.builder()
                .email(requestCreateRequestRoleAccDataDto.email())
                .phone_number(requestCreateRequestRoleAccDataDto.phone_number())
                .enabled(false)
                .name(requestCreateRequestRoleAccDataDto.name())
                .user(user)
                .build();
        deliveryRepository.save(sellerEntity);
    }
}
