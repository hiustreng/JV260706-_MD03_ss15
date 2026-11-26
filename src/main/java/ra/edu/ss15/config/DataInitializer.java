package ra.edu.ss15.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ra.edu.ss15.entity.Product;
import ra.edu.ss15.entity.User;
import ra.edu.ss15.repository.ProductRepository;
import ra.edu.ss15.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            userRepository.saveAll(List.of(
                User.builder()
                    .email("admin@milktea.com")
                    .phone("0901111111")
                    .password(passwordEncoder.encode("123456"))
                    .role("ROLE_ADMIN")
                    .build(),
                User.builder()
                    .email("staff@milktea.com")
                    .phone("0902222222")
                    .password(passwordEncoder.encode("123456"))
                    .role("ROLE_STAFF")
                    .build(),
                User.builder()
                    .email("customer@milktea.com")
                    .phone("0903333333")
                    .password(passwordEncoder.encode("123456"))
                    .role("ROLE_CUSTOMER")
                    .build()
            ));
        }

        if (productRepository.count() == 0) {
            productRepository.saveAll(List.of(
                Product.builder()
                    .name("Trà Sữa Trân Châu Đường Đen")
                    .description("Trà đen đậm vị kết hợp sữa tươi và trân châu đường đen dẻo thơm")
                    .price(new BigDecimal("35000"))
                    .size("L")
                    .toppings("Trân châu đen, Kem cheese")
                    .build(),
                Product.builder()
                    .name("Trà Đào Cam Sả")
                    .description("Trà thanh mát với miếng đào giòn và hương sả tự nhiên")
                    .price(new BigDecimal("32000"))
                    .size("M")
                    .toppings("Đào miếng, Thạch đào")
                    .build()
            ));
        }
    }
}
