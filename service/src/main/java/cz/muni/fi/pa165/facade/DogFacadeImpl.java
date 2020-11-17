package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.DogDTO;
import cz.muni.fi.pa165.entity.Dog;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * DogFacade interface implementation
 *
 * @author Aneta Moravcikova, 456444
 */
@Service
@Transactional
public class DogFacadeImpl implements DogFacade {

    @Autowired
    private BeanMappingService beanMappingService;
    @Autowired
    private DogService dogService;

    public DogFacadeImpl() {
    }

    public DogFacadeImpl(BeanMappingService beanMappingService, DogService dogService) {
        this.beanMappingService = beanMappingService;
        this.dogService = dogService;
    }

    @Override
    public Long createDog(DogDTO dogDTO) {
        Dog dog = beanMappingService.mapTo(dogDTO, Dog.class);
        return dogService.create(dog);
    }

    @Override
    public void updateDog(DogDTO dogDTO) {
        dogService.update(beanMappingService.mapTo(dogDTO, Dog.class));
    }

    @Override
    public void deleteDog(DogDTO dogDTO) {
        dogService.delete(beanMappingService.mapTo(dogDTO, Dog.class));
    }

    @Override
    public DogDTO getDog(Long id) {
        Dog dog = dogService.get(id);
        return beanMappingService.mapTo(dog, DogDTO.class);
    }

    @Override
    public List<DogDTO> getAllDogs() {
        return beanMappingService.mapTo(dogService.getAll(), DogDTO.class);
    }
}
