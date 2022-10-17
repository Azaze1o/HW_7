package guru.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.sourceJson.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class TestJackson {

    String jsonFile = "Shop.json";

    @DisplayName("Json check value")
    @Test
    void jsonCheckValueTest() throws Exception {
        File file = new File("src/test/resources/"+ jsonFile);
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(file, Product.class);
        assertThat(product.id).isEqualTo(456);
        assertThat(product.category).isEqualTo("jeans");
        assertThat(product.name).isEqualTo("blue jeans");
        assertThat(product.available).isEqualTo(true);
        assertThat(product.tags[2]).isEqualTo("woman");
        assertThat(product.size.get("usSize")).isEqualTo(50);
    }
}
