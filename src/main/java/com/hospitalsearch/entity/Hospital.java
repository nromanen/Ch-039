package com.hospitalsearch.entity;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 *
 * @author Oleksandr Mukonin,Pavel Kuz'
 *
 */
@Entity
@Table(name = "hospital")
@Indexed
@NamedQueries({
	@NamedQuery(name = Hospital.GET_LIST_BY_BOUNDS, query = Hospital.GET_LIST_BY_BOUNDS_QUERY)
})
@AnalyzerDefs(value = {
		@AnalyzerDef(name = "ngram", 
					 tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
							 	 @TokenFilterDef(factory = StandardFilterFactory.class),
							 	 @TokenFilterDef(factory = StopFilterFactory.class),
							 	 @TokenFilterDef(factory = NGramFilterFactory.class, params = {
							 			 @Parameter(name = "minGramSize", value = "4"),
							 			 @Parameter(name = "maxGramSize", value = "8") }) 
							 	 })
})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class Hospital {

	static final String GET_LIST_BY_BOUNDS_QUERY = "from Hospital h where "
			+ "(latitude < :nelat) and (latitude > :swlat) and "
			+ "(longitude < :nelng) and (longitude > :swlng)";
	public static final String GET_LIST_BY_BOUNDS = "GET_LIST_BY_BOUNDS";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hospital_gen")
	@SequenceGenerator(name = "hospital_gen", sequenceName = "hospital_id_seq", initialValue = 1, allocationSize = 1)
	@DocumentId
	private Long id;

	@NotEmpty
	@Size(min = 5, max = 50)
	@Column(nullable = false)
	@Field(analyze = Analyze.YES, analyzer = @Analyzer(definition = "ngram"))
	private String name;

	@NotNull
	@Min(-90)
	@Max(90)
	@Column(nullable = false)
	private Double latitude;

	@NotNull
	@Min(-180)
	@Max(180)
	@Column(nullable = false)
	private Double longitude;

	@Embedded
	@Valid
	@IndexedEmbedded
	@AttributeOverrides({
		@AttributeOverride(name = "city", column = @Column(name = "city")),
		@AttributeOverride(name = "country", column = @Column(name = "country")),
		@AttributeOverride(name = "street", column = @Column(name = "street")),
		@AttributeOverride(name = "building", column = @Column(name = "building"))
	})
	private HospitalAddress address = new HospitalAddress();

	@Size(max = 150)
	@Column(nullable = false)
	private String description;

	@Column(name = "imagepath")
	private String imagePath;

	@JsonIgnore
	@OneToMany(mappedBy="hospital",cascade=CascadeType.ALL)
	@Cache(region="entityCache",usage=CacheConcurrencyStrategy.READ_ONLY)
	@IndexedEmbedded
	private List<Department> departments;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	private List<User> managers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public HospitalAddress getAddress() {
		return address;
	}

	public void setAddress(HospitalAddress address) {
		this.address = address;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	
	public List<User> getManagers() {
		return managers;
	}

	public void setManagers(List<User> managers) {
		this.managers = managers;
	}
}
