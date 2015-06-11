#!/bin/sh
skip_install=false
if [ "$#" -gt 0 ]; then
	if [ "$1" == "true" ]; then
		skip_install=true
    elif [ "$1" == "-h" ]; then
        echo "Usage: `basename $0` [skip_install]"
        exit
	fi
fi

if [ "$skip_install" == "false" ]; then
	# Execute maven install to make sure mvn site can resolve the contextproject-core dependency in the desktop submodule
	echo ${TEXT_RED}Executing mvn install -DskiptTests=true, as somehow mvn site makes the desktop module unable to find it\'s core dependency otherwise${RESET_FORMATTING}
	mvn install -DskipTests=true
fi

# Execute maven site
echo ${TEXT_RED}Executing the actual mvn site command${RESET_FORMATTING}
mvn site

# Copy all submodule sites to the main site
for i in */pom.xml; do
    submodule=`dirname "${i}"`
    echo ${TEXT_RED}Copying the ${submodule} module site${RESET_FORMATTING}
    mkdir -p target/site/contextproject-${submodule}
    cp -r ${submodule}/target/site/* target/site/contextproject-${submodule}/
done

echo ${TEXT_GREEN}Checkstyle and PMD coverage reports are in the parent site, while the coverage and findbugs report is only available at the submodule level.${RESET_FORMATTING}
