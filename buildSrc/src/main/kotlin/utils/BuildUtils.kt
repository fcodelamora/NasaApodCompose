/*
 * Copyright 2019 vmadalin.com, et al.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package utils

import org.gradle.api.Project
import java.util.Properties

private const val PROJECT_PROPERTIES_FILE_NAME = "project.properties"

/**
 * Util to obtain property declared on `$projectRoot/local.properties` file.
 *
 * @param propertyName the name of declared property
 * @param project the project reference
 *
 * @return the value of property name, otherwise throw [Exception]
 */
fun getLocalProperty(propertyName: String, project: Project): String {
    val localProperties = Properties().apply {
        val localPropertiesFile = project.rootProject.file(PROJECT_PROPERTIES_FILE_NAME)
        if (localPropertiesFile.exists()) {
            load(localPropertiesFile.inputStream())
        }
    }

    return localProperties.getProperty(propertyName)
        ?: throw NoSuchFieldException(
            "Property not defined: $propertyName\nCreate a project.properties file in " +
                "the project root directory and add apiKeys.nasaApiKey=\"your_api_key_from_nasa_api\"\nAPI Key " +
                "generation available at https://api.nasa.gov/"
        )

}
