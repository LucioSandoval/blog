package com.sistema.blog.service.implementation;

import com.sistema.blog.dto.PublicationDTO;
import com.sistema.blog.dto.PublicationResponDTO;
import com.sistema.blog.exception.ResourceNotFoundException;
import com.sistema.blog.model.Publication;
import com.sistema.blog.repository.IPublicationRepository;
import com.sistema.blog.service.IPublicationService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PublicationServiceImpl implements IPublicationService {
  @Autowired
  private IPublicationRepository publicationRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public PublicationDTO createPublication(PublicationDTO publicacionDTO) {
    Publication publication;
    publication = mapeoEntidad(publicacionDTO);
    Publication nuevaPublicacion = publicationRepository.save(publication);
    PublicationDTO publicationDTO;
    publicationDTO = mapeoDTO(nuevaPublicacion);

    return publicationDTO;
  }

  @Override
  public PublicationResponDTO getPublicaciones(int numeroPagina,int cantidadPagina,String ordenarPor,String sortDir) {

    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
        : Sort.by(ordenarPor).descending();
    Pageable pageable = PageRequest.of(numeroPagina, cantidadPagina, sort);

    Page<Publication> publicationPage = publicationRepository.findAll(pageable);

    List<Publication> publicationList = publicationPage.getContent();
    List<PublicationDTO> contenido = publicationList.stream().map(publication -> mapeoDTO(publication)).collect(Collectors.toList());

    PublicationResponDTO publicationRespon = new PublicationResponDTO();
    publicationRespon.setContenido(contenido);
    publicationRespon.setNumeroPagina(publicationPage.getNumber());
    publicationRespon.setMedidaPagina(publicationPage.getSize());
    publicationRespon.setTotalElementos(publicationPage.getTotalElements());
    publicationRespon.setTotalPaginas(publicationPage.getTotalPages());
    publicationRespon.setUltima(publicationPage.isLast());
    return publicationRespon;
  }

  @Override
  public PublicationDTO publicationFindById(long id) {
    Publication publication = publicationRepository.findById(id)
        .orElseThrow( () -> new ResourceNotFoundException("publicacion", "id", id));
    return mapeoDTO(publication);
  }

  @Override
  public PublicationDTO editPublication(PublicationDTO publicationDTO, long id) {
    Publication publication = publicationRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("publicacion", "id", id));
    publication.setTitulo(publicationDTO.getTitulo());
    publication.setDescripcion(publicationDTO.getDescripcion());
    publication.setContenido(publicationDTO.getContenido());
    Publication publicationEditado = publicationRepository.save(publication);
    PublicationDTO publicationDTOEditado = mapeoDTO(publicationEditado);
    return publicationDTOEditado;
  }

  @Override
  public void deletePublication(long id) {
    Publication publication = publicationRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("publicacion", "id", id));
    publicationRepository.delete(publication);
  }

  // Convertir Entidad en DTO
  private PublicationDTO mapeoDTO(Publication publication){
    PublicationDTO publicacionDTO = modelMapper.map(publication, PublicationDTO.class);

    return publicacionDTO;
  }
  //Mapear entidad
  private Publication mapeoEntidad(PublicationDTO publicacionDTO){
    Publication publication = modelMapper.map(publicacionDTO, Publication.class);
    return publication;
  }


}
