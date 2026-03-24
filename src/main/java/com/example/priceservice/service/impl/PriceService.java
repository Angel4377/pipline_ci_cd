package com.example.priceservice.service.impl;
import java.util.List; import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import com.example.priceservice.service.IRabbitMQService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.priceservice.dao.IPriceRepository;
import com.example.priceservice.dto.PriceDTO;
import com.example.priceservice.entities.Price;
import com.example.priceservice.exception.RequestException;
import com.example.priceservice.mapping.PriceMapper;
import com.example.priceservice.mid.IUserHttp;
import com.example.priceservice.service.IPriceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "prices")
public class PriceService implements IPriceService {

    private final IPriceRepository priceRepository;
    private final PriceMapper priceMapper;
    private final MessageSource messageSource;
    private final IUserHttp userHttp;
    private final IRabbitMQService rabbitMQService;

    @Transactional(readOnly = true)
    public List<PriceDTO> getPrices() {
        return StreamSupport.stream(priceRepository.findAll().spliterator(), false)
                .map(priceMapper::toPriceDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(key = "#id")
    @Transactional(readOnly = true)
    public PriceDTO getPrice(Long id) {
        return priceMapper.toPriceDTO(priceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("price.notfound", new Object[]{id}, Locale.getDefault())
                )));
    }

    @Transactional
    public PriceDTO createPrice(PriceDTO priceDTO) {

        // Vérifier l'utilisateur
        System.out.println(priceDTO.getUserId());
        System.out.println("User : " + userHttp.getUserById(priceDTO.getUserId()));
        if (userHttp.getUserById(priceDTO.getUserId()) == null) {
            throw new RequestException(
                    messageSource.getMessage("user.notfound", new Object[]{priceDTO.getUserId()}, Locale.getDefault()),
                    HttpStatus.BAD_REQUEST
            );
        }

        Price priceEntity = priceRepository.save(priceMapper.fromDTO(priceDTO));

        // Envoyer l'alerte via RabbitMQ
        rabbitMQService.sendPriceAlert(priceMapper.toPriceDTO(priceEntity));

        return priceMapper.toPriceDTO(priceEntity);
    }

    @CachePut(key = "#id")
    @Transactional
    public PriceDTO updatePrice(Long id, PriceDTO priceDTO) {

        if (userHttp.getUserById(priceDTO.getUserId()) == null) {
            throw new RequestException(
                    messageSource.getMessage("user.notfound", new Object[]{priceDTO.getUserId()}, Locale.getDefault()),
                    HttpStatus.BAD_REQUEST
            );
        }

        return priceRepository.findById(id)
                .map(entity -> {
                    entity.setProductName(priceDTO.getProductName());
                    entity.setPrice(priceDTO.getPrice());
                    entity.setSellerName(priceDTO.getSellerName());
                    entity.setLocation(priceDTO.getLocation());
                    entity.setSource(priceDTO.getSource() != null ? Price.PriceSource.valueOf(priceDTO.getSource()) : null);
                    entity.setVerified(priceDTO.getVerified());
                    entity.setCollectedDate(priceDTO.getCollectedDate());

                    return priceMapper.toPriceDTO(priceRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("price.notfound", new Object[]{id}, Locale.getDefault())
                ));
    }

    @CacheEvict(key = "#id")
    @Transactional
    public void deletePrice(Long id) {
        Price entity = priceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("price.notfound", new Object[]{id}, Locale.getDefault())
                ));
        try {
            priceRepository.delete(entity);
        } catch (Exception e) {
            throw new RequestException(
                    messageSource.getMessage("price.errordeletion", new Object[]{id}, Locale.getDefault()),
                    HttpStatus.CONFLICT
            );
        }
    }

    @Override
    public Price getPriceById(Long id) {
        return null;
    }
}



