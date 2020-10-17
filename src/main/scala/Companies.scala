import java.io.{File, FileWriter, IOException}
import java.nio.file.Files
import java.util

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.{JacksonXmlCData, JacksonXmlElementWrapper, JacksonXmlProperty, JacksonXmlRootElement}
import com.fasterxml.jackson.module.scala.DefaultScalaModule


@JacksonXmlRootElement(localName = "companies")
case class Companies(@JacksonXmlProperty(localName = "company")
                     @JacksonXmlCData
                     @JacksonXmlElementWrapper(useWrapping = false)
                     companies: util.List[Company])

@JacksonXmlProperty(localName = "company")
case class Company(country: String,
                   @JsonProperty("company-id") company_id: String,
                   address: String,
                   url: String,
                   @JsonProperty("coordinates") coordinates: Coordinates,
                   @JsonProperty("actualization-date") actualization_date: String,
                   @JsonProperty("feature-boolean_public") feature_boolean_public: String,
                   @JsonProperty("feature-boolean_cash") feature_boolean_cash: String,
                   name: String,
                   @JsonProperty("working-time") working_time: String,
                   @JsonProperty("rubric-id") rubric_id: String
                  )

@JsonProperty("coordinates")
case class Coordinates(lat: String, lon: String)


@throws[IOException]
object ScriptDefinition {
  def getScript(inputFileName: String, outputFileName: String) = {
    val file = new File(inputFileName)
    val content = new String(Files.readAllBytes(file.toPath))
    println(content)
    val newContent = content
      .replaceAll(" lang=\"ru\"", "")
      .replaceAll("<feature-boolean name=\"public\" value=\"1\"/>", "<feature-boolean_public>1</feature-boolean_public>")
      .replaceAll("<feature-boolean name=\"public\" value=\"0\"/>", "<feature-boolean_public>0</feature-boolean_public>")
      .replaceAll("<feature-boolean name=\"cash_in\" value=\"1\"/>", "<feature-boolean_cash>1</feature-boolean_cash>")
      .replaceAll("<feature-boolean name=\"cash_in\" value=\"0\"/>", "<feature-boolean_cash>0</feature-boolean_cash>")
      .replaceAll("'", " ")
    val mapper = new XmlMapper()
    mapper.registerModule(DefaultScalaModule)
    val value = mapper.readValue(newContent, classOf[Companies])
    println(value.companies.size)
    // Сделаем sql-ник
    val list = value.companies
    var fileName = ""
    try fileName = if (outputFileName == null) "output.txt" else outputFileName
    catch {
      case e: ArrayIndexOutOfBoundsException =>
        fileName = "output.txt"
    }
    println("fileName : " + fileName)
    val writer = new FileWriter(fileName)
    for (i <- 0 until list.size) {
      val company = list.get(i)
      val COUNTRY = company.country
      val COMPANY_ID = company.company_id
      val ADDRESS = company.address
      val url = company.url
      val output = String.format("insert into go_geo_obj_atm_partner1(country, company_id, address, url, latitude, longtitude, actualization_date, feature_public, feature_cash_in, name, working_time, rubric_id) " + "values ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s'); \n", COUNTRY, COMPANY_ID, ADDRESS, url, company.coordinates.lat, company.coordinates.lon, company.actualization_date, company.feature_boolean_public, company.feature_boolean_cash, company.name, company.working_time, company.rubric_id)
      writer.write(output)
      if (i % 800 == 0) writer.write("commit;\n")
    }
    writer.close()
  }
}