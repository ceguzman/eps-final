package co.edu.poligran.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.poligran.web.rest.TestUtil;

public class DiagnosticoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Diagnostico.class);
        Diagnostico diagnostico1 = new Diagnostico();
        diagnostico1.setId(1L);
        Diagnostico diagnostico2 = new Diagnostico();
        diagnostico2.setId(diagnostico1.getId());
        assertThat(diagnostico1).isEqualTo(diagnostico2);
        diagnostico2.setId(2L);
        assertThat(diagnostico1).isNotEqualTo(diagnostico2);
        diagnostico1.setId(null);
        assertThat(diagnostico1).isNotEqualTo(diagnostico2);
    }
}
