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

// Endpoint from swagger https://catalog.redhat.com/api/containers/v1/ui/#/Repositories/graphql.images.get_images_by_repo
def request = "https://catalog.redhat.com/api/containers/v1/repositories/registry/registry.access.redhat.com/repository/rhbk%2Fkeycloak-rhel9/images?include=data.freshness_grades.grade&include=data.parsed_data.labels&include=data.repositories.published_date&include=data.repositories.tags.name&page_size=1&page=0"

try {
    // Fetching the JSON response
    def responseText = new URL(request).text

    println responseText

} catch (Exception e) {
    println "Error fetching data: ${e.message}"
    e.printStackTrace()
}