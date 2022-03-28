package co.edu.poligran.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.poligran.web.rest.TestUtil;

public class IndicadoresSaludTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndicadoresSalud.class);
        IndicadoresSalud indicadoresSalud1 = new IndicadoresSalud();
        indicadoresSalud1.setId(1L);
        IndicadoresSalud indicadoresSalud2 = new IndicadoresSalud();
        indicadoresSalud2.setId(indicadoresSalud1.getId());
        assertThat(indicadoresSalud1).isEqualTo(indicadoresSalud2);
        indicadoresSalud2.setId(2L);
        assertThat(indicadoresSalud1).isNotEqualTo(indicadoresSalud2);
        indicadoresSalud1.setId(null);
        assertThat(indicadoresSalud1).isNotEqualTo(indicadoresSalud2);
    }
}
