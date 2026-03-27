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

    private static final String MODULE_SLICES = "com.eventshub.modules.(*)..";
    private static final String MODULE_PACKAGE = "com.eventshub.modules..";
    private static final String MODULE_CORE_SLICES = "com.eventshub.modules.(*).core..";
    private static final String SHARED_PACKAGE = "com.eventshub.shared..";
    private static final String ANY_CORE = "..core..";
    private static final String ANY_DOMAIN = "..core.domain..";
    private static final String ANY_APPLICATION = "..core.application..";
    private static final String ANY_INFRA = "..infra..";

    @ArchTest
    static final ArchRule core_must_be_pure = noClasses()
            .that().resideInAPackage(ANY_CORE)
            .should().dependOnClassesThat().resideInAPackage(ANY_INFRA)
            .because("The core (domain + application)  should not have access to infrastructure details");

    @ArchTest
    static final ArchRule domain_must_be_pure = noClasses()
            .that().resideInAPackage(ANY_DOMAIN)
            .should().dependOnClassesThat().resideInAPackage(ANY_APPLICATION)
            .because("The domain is the core of the business and should be independent of application flows");

    @ArchTest
    static final ArchRule core_modules_must_be_isolated = slices()
            .matching(MODULE_SLICES)
            .should().notDependOnEachOther()
            .ignoreDependency(resideInAPackage(MODULE_SLICES), resideInAPackage(SHARED_PACKAGE))
            .ignoreDependency(resideInAPackage(ANY_INFRA), resideInAPackage(ANY_INFRA))
            .because("Core modules must be isolated. Infra-to-infra dependencies are allowed when necessary");


    @ArchTest
    static final ArchRule core_must_not_depend_on_other_modules_core = slices()
            .matching(MODULE_CORE_SLICES)
            .should().notDependOnEachOther()
            .because("Core must be completely isolated from other modules including their core");

    @ArchTest
    static final ArchRule shared_must_not_depend_on_business_modules = noClasses()
            .that().resideInAPackage(SHARED_PACKAGE)
            .should().dependOnClassesThat().resideInAPackage(MODULE_PACKAGE)
            .because("The shared is multifunctional and should not be subject to specific business rules");
}