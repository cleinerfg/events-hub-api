package com.eventshub;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = "com.eventshub", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

    private static final String MODULE_PACKAGE = "com.eventshub.modules.(*)..";
    private static final String SHARED_PACKAGE = "com.eventshub.shared..";
    private static final String ANY_CORE = "..core..";
    private static final String ANY_INFRA = "..infra..";

    @ArchTest
    static final ArchRule core_must_be_pure = noClasses()
            .that().resideInAPackage(ANY_CORE)
            .should().dependOnClassesThat().resideInAPackage(ANY_INFRA)
            .because("The core should not have access to infrastructure details");

    @ArchTest
    static final ArchRule shared_must_not_depend_on_business_modules = noClasses()
            .that().resideInAPackage(SHARED_PACKAGE)
            .should().dependOnClassesThat().resideInAPackage("com.eventshub.modules..")
            .because("The shared is multifunctional and should not be subject to specific business rules");

    @ArchTest
    static final ArchRule modules_must_be_isolated_except_from_shared = slices()
            .matching(MODULE_PACKAGE)
            .should().notDependOnEachOther()
            .ignoreDependency(resideInAPackage(MODULE_PACKAGE), resideInAPackage(SHARED_PACKAGE))
            .because("Business modules should be isolated from each other, but they can use shared");
}