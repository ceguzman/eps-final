package co.edu.poligran;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("co.edu.poligran");

        noClasses()
            .that()
                .resideInAnyPackage("co.edu.poligran.service..")
            .or()
                .resideInAnyPackage("co.edu.poligran.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..co.edu.poligran.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
