package net.smartcosmos.cluster.userdetails.config;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;

import net.smartcosmos.cluster.userdetails.domain.ConfiguredUserDetails;

/**
 * Configuration properties for dummy user.
 * <p>
 * Multiple users can be configured in the service config using the following syntax
 * <pre>
 * {@code
 * smartcosmos:
 *      security:
 *        users[user]:
 *          username: user
 *          password: password
 *          tenant-urn: urn:tenant:uuid:DAF0D088-75A5-4C65-B331-24F26A30A331
 *          user-urn: urn:user:uuid:6E3718FA-3DDD-4079-89C4-D401FAC78CA1
 *          authorities:
 *            - https://authorities.smartcosmos.net/things/create
 *            - https://authorities.smartcosmos.net/things/read
 *            - https://authorities.smartcosmos.net/things/update
 *            - https://authorities.smartcosmos.net/things/delete
 *        users[jim]:
 *          username: jim
 *          password: jimpassword
 *          tenant-urn: urn:tenant:uuid:13977FE9-6B12-4273-9522-2789D51F4A6E
 *          user-urn: urn:user:uuid:F0586109-CD90-4645-BFD5-BC641D7EC635
 *          authorities:
 *            - https://authorities.smartcosmos.net/things/read
 *            - https://authorities.smartcosmos.net/metadata/read
 *            - https://authorities.smartcosmos.net/relationships/read
 *}
 * </pre>
 * </p>
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ConfigurationProperties("smartcosmos.security")
public class UserDetailsDevelopmentServiceProperties {

    private Map<String, ConfiguredUserDetails> users = new HashMap<>();

}
