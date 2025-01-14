/**
 * The aim is to access the Red Hat Ecosystem Catalog, and output a report
 * showing information on the most recent image in each content stream for rhbk/keycloakrhel9.
 * The report should be in JSON format with the following keys, per content stream.
 * Example:
 * {
 *     "contentStream": "26.0",
 *     "vcsRef": "84d4042f71c665c0636aa5ffda537f52c21aa06c",
 *     "publishedDate": "2024-11-13T14:10:23+00:00",
 *     "freshnessGrade": "A"
 * }
**/
import groovy.json.JsonSlurper

// Endpoint from swagger https://catalog.redhat.com/api/containers/v1/ui/#/Repositories/graphql.images.get_images_by_repo (descending order by published date so most recent is first)
def request = "https://catalog.redhat.com/api/containers/v1/repositories/registry/registry.access.redhat.com/repository/rhbk%2Fkeycloak-rhel9/images?include=data.freshness_grades.grade&include=data.parsed_data.labels&include=data.repositories.published_date&include=data.repositories.tags.name&page_size=5&page=0&sort_by=repositories.published_date%5Bdesc%5D"

try {
    // Fetching the JSON response
    def responseText = new URL(request).text

    println responseText

    // Parsing respone
    def parsedResponseText = new JsonSlurper().parseText(responseText)

    println parsedResponseText

    // Extract the list of images (utilizing safe navigator to prevent exception)
    def images = parsedResponseText?.data

    if(images) {
        // Iterate over each image
        images.each { image ->
            def tags = image?.repositories?.tags?.name  // List of tag names
            def publishedDate = image?.repositories?.published_date ?: "N/A"
            def vcsRef = image?.parsed_data?.labels.find { it.name == "vcs-ref"}?.value ?: "N/A"
            def freshnessGrade = image?.freshness_grades?.grade?.getAt(0) ?: "N/A"

            println(tags + publishedDate + vcsRef + freshnessGrade)
        }
    } else {
        println "No images found"
    }

} catch (Exception e) {
    println "Error fetching data: ${e.message}"
    e.printStackTrace()
}