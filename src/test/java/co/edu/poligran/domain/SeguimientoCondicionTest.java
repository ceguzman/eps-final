package co.edu.poligran.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.poligran.web.rest.TestUtil;

public class SeguimientoCondicionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeguimientoCondicion.class);
        SeguimientoCondicion seguimientoCondicion1 = new SeguimientoCondicion();
        seguimientoCondicion1.setId(1L);
        SeguimientoCondicion seguimientoCondicion2 = new SeguimientoCondicion();
        seguimientoCondicion2.setId(seguimientoCondicion1.getId());
        assertThat(seguimientoCondicion1).isEqualTo(seguimientoCondicion2);
        seguimientoCondicion2.setId(2L);
        assertThat(seguimientoCondicion1).isNotEqualTo(seguimientoCondicion2);
        seguimientoCondicion1.setId(null);
        assertThat(seguimientoCondicion1).isNotEqualTo(seguimientoCondicion2);
    }
}
