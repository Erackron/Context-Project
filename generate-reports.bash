#!/bin/sh
# Execute maven install to make sure mvn site can resolve the contextproject-core dependency in the desktop submodule
echo ${TEXT_RED}Executing mvn install -DskiptTests=true, as somehow mvn site makes the desktop module unable to find it\'s core dependency otherwise${RESET_FORMATTING}
mvn install -DskipTests=true

# Execute maven site
echo ${TEXT_RED}Executing the actual mvn site command${RESET_FORMATTING}
mvn site

# Copy core report
echo ${TEXT_RED}Copying the core module site${RESET_FORMATTING}
mkdir -p target/site/contextproject-core
cp -r core/target/site/* target/site/contextproject-core/

# Copy desktop report
echo ${TEXT_RED}Copying the desktop module site${RESET_FORMATTING}
mkdir -p target/site/contextproject-desktop
cp -r desktop/target/site/* target/site/contextproject-desktop


echo ${TEXT_GREEN}Checkstyle and PMD coverage reports are in the parent site, while the coverage and findbugs report is only available at the submodule level.${RESET_FORMATTING}
