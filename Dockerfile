FROM smartcosmos/service
MAINTAINER SMART COSMOS Platform Core Team

ADD target/smartcosmos-*.jar  /opt/smartcosmos/smartcosmos-user-details-service.jar

CMD ["java", "-jar", "/opt/smartcosmos/smartcosmos-user-details-service.jar"]
