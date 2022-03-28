package co.edu.poligran.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.poligran.web.rest.TestUtil;

public class AfiliadoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Afiliado.class);
        Afiliado afiliado1 = new Afiliado();
        afiliado1.setId(1L);
        Afiliado afiliado2 = new Afiliado();
        afiliado2.setId(afiliado1.getId());
        assertThat(afiliado1).isEqualTo(afiliado2);
        afiliado2.setId(2L);
        assertThat(afiliado1).isNotEqualTo(afiliado2);
        afiliado1.setId(null);
        assertThat(afiliado1).isNotEqualTo(afiliado2);
    }
}
