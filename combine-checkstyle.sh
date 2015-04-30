#!/bin/sh
core_xml=$(head core/target/checkstyle-result.xml -n -1)
desktop_xml=$(tail core/target/checkstyle-result.xml -n +3)
printf "%s\n" "$core_xml" > target/checkstyle-result.xml
printf "%s\n" "$desktop_xml" >> target/checkstyle-result.xml
