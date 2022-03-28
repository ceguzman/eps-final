package co.edu.poligran.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.poligran.web.rest.TestUtil;

public class ControlProfesionalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControlProfesional.class);
        ControlProfesional controlProfesional1 = new ControlProfesional();
        controlProfesional1.setId(1L);
        ControlProfesional controlProfesional2 = new ControlProfesional();
        controlProfesional2.setId(controlProfesional1.getId());
        assertThat(controlProfesional1).isEqualTo(controlProfesional2);
        controlProfesional2.setId(2L);
        assertThat(controlProfesional1).isNotEqualTo(controlProfesional2);
        controlProfesional1.setId(null);
        assertThat(controlProfesional1).isNotEqualTo(controlProfesional2);
    }
}
