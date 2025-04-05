package com.pharmeast.consent.service.impl;

@org.springframework.stereotype.Service
@lombok.RequiredArgsConstructor
public class ServiceServiceImpl
    implements com.pharmeast.consent.service.ServiceService {
//
//    private final com.pharmeast.consent.repository.ServiceRepository serviceRepository;
//    private final com.pharmeast.consent.mapper.ServiceMapper serviceMapper;
//    private final com.pharmeast.consent.repository.EmployeeRepository employeeRepository;
//
//    @Override
//    public com.pharmeast.consent.dto.ServiceDto createService(
//        final com.pharmeast.consent.dto.ServiceDto serviceDto
//    ) {
//
//        final java.util.List< String > missingFields = new java.util.ArrayList<>();
//
//        if ( serviceDto.getName() == null ) missingFields.add( "name" );
//        if ( serviceDto.getDescription() == null ) {
//            missingFields.add( "description" );
//        }
//        if ( serviceDto.getCreatedByEmail() == null ) {
//            missingFields.add( "createdBy" );
//        }
//        if ( serviceDto.getStatus() == null ) missingFields.add( "status" );
//
//        if ( !missingFields.isEmpty() ) {
//            throw new com.pharmeast.consent.exception.MissingParameterException(
//                "Missing required fields: " + String.join( ", ", missingFields ) );
//        }
//
//        final com.pharmeast.consent.entity.Service service = serviceMapper.toEntity(
//            serviceDto );
//        final com.pharmeast.consent.entity.Service savedService = serviceRepository.save(
//            service );
//        return serviceMapper.toDto( savedService );
//    }
//
//    @Override
//    public java.util.List< String > getAllServices() {
//
//        return serviceRepository.findAll().stream().map(
//            com.pharmeast.consent.entity.Service::getName ).toList();
//    }
//
//    @Override
//    public com.pharmeast.consent.dto.ServiceDto getServiceByName(
//        final String serviceName
//    ) {
//
//        return serviceRepository.findById( serviceName ).map( serviceMapper::toDto )
//                                .orElseThrow(
//                                    () -> new RuntimeException( "Service not found" ) );
//    }
//
//    @Override
//    public com.pharmeast.consent.dto.ServiceDto updateService(
//        final com.pharmeast.consent.dto.ServiceDto serviceDto
//    ) {
//
//        if ( serviceDto.getName() == null ) {
//            throw new com.pharmeast.consent.exception.MissingParameterException(
//                "Service name is required" );
//        }
//
//        final com.pharmeast.consent.entity.Service existingService = serviceRepository
//            .findById( serviceDto.getName() ).orElseThrow(
//                () -> new RuntimeException( "Service not found" ) );
//        final com.pharmeast.consent.entity.Service tempService = serviceMapper.toEntity(
//            serviceDto );
//
//        if ( serviceDto.getDescription() != null ) {
//            existingService.setDescription( tempService.getDescription() );
//        }
//        if ( serviceDto.getStatus() != null ) {
//            existingService.setStatus( tempService.getStatus() );
//        }
//        if ( serviceDto.getCreatedByEmail() != null ) {
//            existingService.setCreatedBy( tempService.getCreatedBy() );
//        }
//
//        final com.pharmeast.consent.entity.Service updatedService
//            = serviceRepository.save( existingService );
//        return serviceMapper.toDto( updatedService );
//    }
//
//    @Override
//    public String deleteService( final String serviceName ) {
//
//        final com.pharmeast.consent.entity.Service service = serviceRepository.findById(
//            serviceName ).orElseThrow(
//            () -> new com.pharmeast.consent.exception.MissingServiceException(
//                java.text.MessageFormat.format( "{0}", serviceName ) ) );
//        serviceRepository.delete( service );
//        return "Service deleted successfully with name: " + serviceName;
//    }
//
//    @Override
//    public String whoIsOwnerToService( final String serviceName ) {
//
//        final com.pharmeast.consent.entity.Service service = serviceRepository.findById(
//            serviceName ).orElseThrow(
//            () -> new com.pharmeast.consent.exception.MissingServiceException(
//                java.text.MessageFormat.format( "{0}", serviceName ) ) );
//        return service.getCreatedBy().getEmail();
//    }
//
//    @Override
//    public java.util.List< String > whatAreMyRequestedServices(
//        final String employeeEmail
//    ) {
//
//        final com.pharmeast.consent.entity.Employee employee = employeeRepository
//            .findById( employeeEmail ).orElseThrow(
//                () -> new com.pharmeast.consent.exception.MissingEmailException(
//                    java.text.MessageFormat.format( "{0}", employeeEmail ) ) );
//        return employee.getRequestedServices().stream().map(
//            com.pharmeast.consent.entity.Service::getName ).toList();
//    }
//
//    @Override
//    public java.util.List< String > whatAreMyAccessibleServices(
//        final String employeeEmail
//    ) {
//
//        final com.pharmeast.consent.entity.Employee employee = employeeRepository
//            .findById( employeeEmail ).orElseThrow(
//                () -> new com.pharmeast.consent.exception.MissingEmailException(
//                    java.text.MessageFormat.format( "{0}", employeeEmail ) ) );
//        return employee.getAccessibleServices().stream().map(
//            com.pharmeast.consent.entity.Service::getName ).toList();
//    }
//
//    @Override
//    public Boolean reqeustServiceAccess(
//        final String serviceName,
//        final String employeeEmail
//    ) {
//
//        final com.pharmeast.consent.entity.Service service = serviceRepository.findById(
//            serviceName ).orElseThrow(
//            () -> new com.pharmeast.consent.exception.MissingServiceException(
//                serviceName ) );
//        final com.pharmeast.consent.entity.Employee employee = employeeRepository
//            .findById( employeeEmail ).orElseThrow(
//                () -> new com.pharmeast.consent.exception.MissingEmailException(
//                    employeeEmail ) );
//
//        if ( service.getWhoHasRequested().contains( employee ) ) return false;
//
//        service.getWhoHasRequested().add( employee );
//        serviceRepository.save( service );
//        return true;
//    }
//
//    @Override
//    public Boolean removeServiceAccess(
//        final String serviceName,
//        final String employeeEmail
//    ) {
//
//        final com.pharmeast.consent.entity.Service service = serviceRepository.findById(
//            serviceName ).orElseThrow(
//            () -> new com.pharmeast.consent.exception.MissingServiceException(
//                serviceName ) );
//        final com.pharmeast.consent.entity.Employee employee = employeeRepository
//            .findById( employeeEmail ).orElseThrow(
//                () -> new com.pharmeast.consent.exception.MissingEmailException(
//                    employeeEmail ) );
//
//        final boolean removed = service.getWhoHasAccess().remove( employee );
//        if ( removed ) serviceRepository.save( service );
//        return removed;
//    }
//
//    @Override
//    public Boolean isServiceAccessGranted(
//        final String serviceName,
//        final String employeeEmail
//    ) {
//
//        final com.pharmeast.consent.entity.Service service = serviceRepository.findById(
//            serviceName ).orElseThrow(
//            () -> new com.pharmeast.consent.exception.MissingServiceException(
//                java.text.MessageFormat.format( "{0}", serviceName ) ) );
//        return service.getWhoHasAccess().stream().anyMatch(
//            e -> e.getEmail().equals( employeeEmail ) );
//    }
//
//    @Override
//    public java.util.List< String > whatAreMyServices(
//        final String employeeEmail
//    ) {
//
//        final com.pharmeast.consent.entity.Employee employee = employeeRepository
//            .findById( employeeEmail ).orElseThrow(
//                () -> new com.pharmeast.consent.exception.MissingEmailException(
//                    java.text.MessageFormat.format( "{0}", employeeEmail ) ) );
//        return employee.getOwnedServices().stream().map(
//            com.pharmeast.consent.entity.Service::getName ).toList();
//    }
//
//    @Override
//    public Boolean isServiceOwnedByMe(
//        final String serviceName,
//        final String employeeEmail
//    ) {
//
//        return whoIsOwnerToService( serviceName ).equals( employeeEmail );
//    }
//
//    @Override
//    public String transferServiceOwnership(
//        final String serviceName,
//        final String employeeEmail
//    ) {
//
//        if ( Boolean.FALSE.equals( isServiceOwnedByMe( serviceName, employeeEmail ) ) ) {
//            throw new com.pharmeast.consent.exception.EmployeeNotfoundException(
//                "You are not the owner of this service" );
//        }
//
//        final com.pharmeast.consent.entity.Service service = serviceRepository.findById(
//            serviceName ).orElseThrow(
//            () -> new com.pharmeast.consent.exception.MissingServiceException(
//                java.text.MessageFormat.format( "{0}", serviceName ) ) );
//        final com.pharmeast.consent.entity.Employee newOwner = employeeRepository
//            .findById( employeeEmail ).orElseThrow(
//                () -> new com.pharmeast.consent.exception.EmployeeNotfoundException(
//                    java.text.MessageFormat.format( "{0}", employeeEmail ) ) );
//
//        service.setCreatedBy( newOwner );
//        serviceRepository.save( service );
//        return "Service ownership transferred successfully to: " + employeeEmail;
//    }
//
//    @Override
//    public Boolean addServiceAccess(
//        final String serviceName,
//        final String employeeEmail
//    ) {
//
//        final com.pharmeast.consent.entity.Service service = serviceRepository.findById(
//            serviceName ).orElseThrow(
//            () -> new com.pharmeast.consent.exception.MissingServiceException(
//                serviceName ) );
//        final com.pharmeast.consent.entity.Employee employee = employeeRepository
//            .findById( employeeEmail ).orElseThrow(
//                () -> new com.pharmeast.consent.exception.MissingEmailException(
//                    employeeEmail ) );
//
//        if ( service.getWhoHasAccess().contains( employee ) ) return false;
//
//        service.getWhoHasAccess().add( employee );
//        serviceRepository.save( service );
//        return true;
//    }
//
//    @Override
//    public java.util.List< String > whoHasRequestedMyService(
//        final String serviceName,
//        final String ownerEmail
//    ) {
//
//        if ( Boolean.FALSE.equals( isServiceOwnedByMe( serviceName, ownerEmail ) ) ) {
//            throw new com.pharmeast.consent.exception.EmployeeNotfoundException(
//                "You are not the owner of this service" );
//        }
//
//        final com.pharmeast.consent.entity.Service service = serviceRepository.findById(
//            serviceName ).orElseThrow(
//            () -> new com.pharmeast.consent.exception.MissingServiceException(
//                serviceName ) );
//
//        return service.getWhoHasRequested().stream().map(
//            com.pharmeast.consent.entity.Employee::getEmail ).toList();
//    }
//
//    @Override
//    public java.util.List< String > whoHasAccessToMyService(
//        final String serviceName,
//        final String employeeEmail
//    ) {
//
//        if ( Boolean.FALSE.equals( isServiceOwnedByMe( serviceName, employeeEmail ) ) ) {
//            throw new com.pharmeast.consent.exception.EmployeeNotfoundException(
//                "You are not the owner of this service" );
//        }
//
//        final com.pharmeast.consent.entity.Service service = serviceRepository.findById(
//            serviceName ).orElseThrow(
//            () -> new com.pharmeast.consent.exception.MissingServiceException(
//                serviceName ) );
//
//        return service.getWhoHasAccess().stream().map(
//            com.pharmeast.consent.entity.Employee::getEmail ).toList();
//    }
//
//    @Override
//    public Boolean grantServiceAccess(
//        final String serviceName,
//        final String employeeEmail
//    ) {
//
//        if ( Boolean.FALSE.equals( isServiceOwnedByMe( serviceName, employeeEmail ) ) ) {
//            throw new com.pharmeast.consent.exception.EmployeeNotfoundException(
//                "You are not the owner of this service" );
//        }
//
//        return addServiceAccess( serviceName, employeeEmail );
//    }
//
//    @Override
//    public Boolean revokeServiceAccess(
//        final String serviceName,
//        final String employeeEmail
//    ) {
//
//        if ( Boolean.FALSE.equals( isServiceOwnedByMe( serviceName, employeeEmail ) ) ) {
//            throw new com.pharmeast.consent.exception.EmployeeNotfoundException(
//                "You are not the owner of this service" );
//        }
//
//        return removeServiceAccess( serviceName, employeeEmail );
//    }
}
