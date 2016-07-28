FROM smartcosmos/service
MAINTAINER SMART COSMOS Platform Core Team

ADD target/smartcosmos-*.jar  /opt/smartcosmos/smartcosmos-user-details-service.jar

CMD ["/opt/smartcosmos/smartcosmos-user-details-service.jar"]
