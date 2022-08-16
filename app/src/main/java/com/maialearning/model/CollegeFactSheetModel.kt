package com.maialearning.model


import com.google.gson.annotations.SerializedName

data class CollegeFactSheetModel(
    @SerializedName("basic_info")
    var basicInfo: BasicInfo,
    @SerializedName("campus_community")
    var campusCommunity: CampusCommunity,
    @SerializedName("admissions")
    var admissions: Admissions,
    @SerializedName("tution_financial")
    var tutionFinancial: TutionFinancial,
    @SerializedName("degree_majors")
    var degreeMajors: DegreeMajors,
    @SerializedName("degree_majors1")
    var degreeMajors1: DegreeMajors1?,
    @SerializedName("varsity_athletic_teams")
    var varsityAthleticTeams: VarsityAthleticTeams,
    @SerializedName("varsity_athletic_sports")
    var varsityAthleticSports: VarsityAthleticSports,
    @SerializedName("varsity_athletic_sports1")
    var varsityAthleticSports1: VarsityAthleticSports1,
    @SerializedName("transfers")
    var transfers: Transfers,
    @SerializedName("services")
    var services: Services
) {
    data class BasicInfo(
        @SerializedName("award")
        var award: List<String>,
        @SerializedName("description")
        var description: String,
        @SerializedName("name")
        var name: String,
        @SerializedName("addr")
        var addr: String,
        @SerializedName("city")
        var city: String,
        @SerializedName("state")
        var state: String,
        @SerializedName("zip")
        var zip: String,
        @SerializedName("phone")
        var phone: String,
        @SerializedName("web_addr")
        var webAddr: String,
        @SerializedName("institution_type")
        var institutionType: String,
        @SerializedName("environment_type")
        var environmentType: String,
        @SerializedName("enr_total")
        var enrTotal: String,
        @SerializedName("term_type")
        var termType: String,
        @SerializedName("distance_from_home")
        var distanceFromHome: Int,
        @SerializedName("distance_from_school")
        var distanceFromSchool: Int,
        @SerializedName("longitude")
        var longitude: String,
        @SerializedName("latitude")
        var latitude: String
    )

    data class CampusCommunity(
        @SerializedName("housing")
        var housing: String,
        @SerializedName("religious")
        var religious: String,
        @SerializedName("student_government")
        var studentGovernment: String,
        @SerializedName("student_newspaper")
        var studentNewspaper: String,
        @SerializedName("literary_magazine")
        var literaryMagazine: String,
        @SerializedName("yearbook")
        var yearbook: String,
        @SerializedName("radio_station")
        var radioStation: String,
        @SerializedName("television_station")
        var televisionStation: String,
        @SerializedName("minority_organizations")
        var minorityOrganizations: String,
        @SerializedName("international_organizations")
        var internationalOrganizations: String,
        @SerializedName("campus_organizations")
        var campusOrganizations: String,
        @SerializedName("other_organizations")
        var otherOrganizations: String,
        @SerializedName("special_opportunities")
        var specialOpportunities: List<String>,
        @SerializedName("total_enrollment")
        var totalEnrollment: String,
        @SerializedName("female_enrollment")
        var femaleEnrollment: Int,
        @SerializedName("male_enrollment")
        var maleEnrollment: Int,
        @SerializedName("in-state")
        var inState: String,
        @SerializedName("out-state")
        var outState: String,
        @SerializedName("foreign")
        var foreign: String,
        @SerializedName("unknown_residence")
        var unknownResidence: String,
        @SerializedName("ug_race")
        var ugRace: UgRace,
        @SerializedName("full_time")
        var fullTime: FullTime,
        @SerializedName("part_time")
        var partTime: PartTime,
        @SerializedName("undergraduate_enroll")
        var undergraduateEnroll: UndergraduateEnroll,
        @SerializedName("graduate_enroll")
        var graduateEnroll: GraduateEnroll,
        @SerializedName("ug_disablility")
        var ugDisablility: String
    ) {
        data class UgRace(
            @SerializedName("american")
            var american: String,
            @SerializedName("asian")
            var asian: String,
            @SerializedName("black")
            var black: String,
            @SerializedName("hispanic")
            var hispanic: String,
            @SerializedName("native")
            var native: String,
            @SerializedName("white")
            var white: String,
            @SerializedName("two_races")
            var twoRaces: String,
            @SerializedName("unknown")
            var unknown: String,
            @SerializedName("alien")
            var alien: String
        )

        data class FullTime(
            @SerializedName("percent")
            var percent: Int,
            @SerializedName("value")
            var value: String
        )

        data class PartTime(
            @SerializedName("percent")
            var percent: Int,
            @SerializedName("value")
            var value: String
        )

        data class UndergraduateEnroll(
            @SerializedName("percent")
            var percent: Int,
            @SerializedName("value")
            var value: String
        )

        data class GraduateEnroll(
            @SerializedName("percent")
            var percent: Int,
            @SerializedName("value")
            var value: String
        )
    }

    data class Admissions(
        @SerializedName("contact")
        var contact: Contact,
        @SerializedName("app_type")
        var appType: String,
        @SerializedName("apply_link")
        var applyLink: Any,
        @SerializedName("ccc_csu_type")
        var cccCsuType: Any,
        @SerializedName("acceptance")
        var acceptance: String,
        @SerializedName("gpa")
        var gpa: String,
        @SerializedName("sat_cr")
        var satCr: SatCr,
        @SerializedName("sat_math")
        var satMath: SatMath,
        @SerializedName("sat_wr")
        var satWr: SatWr,
        @SerializedName("act_comp")
        var actComp: ActComp,
        @SerializedName("act_eng")
        var actEng: ActEng,
        @SerializedName("act_math")
        var actMath: ActMath,
        @SerializedName("sat")
        var sat: String,
        @SerializedName("sat_max")
        var satMax: Int,
        @SerializedName("act")
        var act: String,
        @SerializedName("act_max")
        var actMax: Int,
        @SerializedName("fafsa")
        var fafsa: String,
        @SerializedName("fice")
        var fice: String,
        @SerializedName("application_fee")
        var applicationFee: String,
        @SerializedName("common_app")
        var commonApp: String,
        @SerializedName("application_deadline_final")
        var applicationDeadlineFinal: String,
        @SerializedName("application_deadline_priority")
        var applicationDeadlinePriority: Any,
        @SerializedName("early_action")
        var earlyAction: String,
        @SerializedName("early_decision")
        var earlyDecision: String,
        @SerializedName("admission_interview")
        var admissionInterview: String,
        @SerializedName("sat_act")
        var satAct: String,
        @SerializedName("sat_recommend")
        var satRecommend: Any
    ) {
        data class Contact(
            @SerializedName("admofficer")
            var admofficer: String,
            @SerializedName("admtitle")
            var admtitle: String,
            @SerializedName("adm_phone")
            var admPhone: String,
            @SerializedName("adm_fax")
            var admFax: String,
            @SerializedName("adm_email")
            var admEmail: String
        )

        data class SatCr(
            @SerializedName("25per")
            var per25: String,
            @SerializedName("75per")
            var per: String
        )

        data class SatMath(
            @SerializedName("25per")
            var per25: String,
            @SerializedName("75per")
            var per: String
        )

        data class SatWr(
            @SerializedName("25per")
            var per25: Any,
            @SerializedName("75per")
            var per: Any
        )

        data class ActComp(
            @SerializedName("25per")
            var per25: String,
            @SerializedName("75per")
            var per: String
        )

        data class ActEng(
            @SerializedName("25per")
            var per25: String,
            @SerializedName("75per")
            var per: String
        )

        data class ActMath(
            @SerializedName("25per")
            var per25: String,
            @SerializedName("75per")
            var per: String
        )
    }

    data class TutionFinancial(
        @SerializedName("tution_fees_instate")
        var tutionFeesInstate: Int,
        @SerializedName("tution_fees_outstate")
        var tutionFeesOutstate: Int,
        @SerializedName("books")
        var books: Int,
        @SerializedName("room_board")
        var roomBoard: Int,
        @SerializedName("other_charges")
        var otherCharges: Int,
        @SerializedName("total_instate")
        var totalInstate: Int,
        @SerializedName("total_outstate")
        var totalOutstate: Int,
        @SerializedName("financial_aid")
        var financialAid: FinancialAid,
        @SerializedName("financial_deadlines")
        var financialDeadlines: FinancialDeadlines,
        @SerializedName("financial_office")
        var financialOffice: FinancialOffice
    ) {
        data class FinancialAid(
            @SerializedName("fully_met_student")
            var fullyMetStudent: String,
            @SerializedName("avg_financial_award")
            var avgFinancialAward: String,
            @SerializedName("pell_grant_students")
            var pellGrantStudents: String,
            @SerializedName("institutional_grant_students")
            var institutionalGrantStudents: String,
            @SerializedName("federal_students_loans")
            var federalStudentsLoans: String
        )

        data class FinancialDeadlines(
            @SerializedName("priority_deadline")
            var priorityDeadline: String,
            @SerializedName("regular_deadline")
            var regularDeadline: String,
            @SerializedName("notification_date")
            var notificationDate: String
        )

        data class FinancialOffice(
            @SerializedName("phone1")
            var phone1: String,
            @SerializedName("phone2")
            var phone2: String,
            @SerializedName("mail")
            var mail: String,
            @SerializedName("fafsa_code")
            var fafsaCode: String
        )
    }

    data class DegreeMajors(
        @SerializedName("majors")
        var majors: Majors,
        @SerializedName("program_offered")
        var programOffered: List<String>
    ) {
        data class Majors(
            @SerializedName("Agricultural/Animal/Plant/Veterinary Science and Related Fields")
            var agriculturalAnimalPlantVeterinaryScienceAndRelatedFields: AnimalSciences,
            @SerializedName("Agricultural Business and Management")
            var agriculturalBusinessAndManagement: AnimalSciences,
            @SerializedName("Agribusiness/Agricultural Business Operations")
            var agribusinessAgriculturalBusinessOperations: AnimalSciences,
            @SerializedName("Animal Sciences")
            var animalSciences: AnimalSciences,
            @SerializedName("Animal Sciences, General")
            var animalSciencesGeneral: AnimalSciences,
            @SerializedName("Natural Resources and Conservation")
            var naturalResourcesAndConservation: AnimalSciences,
            @SerializedName("Natural Resources Conservation and Research")
            var naturalResourcesConservationAndResearch: NaturalResourcesConservationAndResearch,
            @SerializedName("Environmental Science")
            var environmentalScience: EnvironmentalScience,
            @SerializedName("Architecture and Related Services")
            var architectureAndRelatedServices: ArchitectureAndRelatedServices,
            @SerializedName("Architecture")
            var architecture: Architecture,
            @SerializedName("Pre-Architecture Studies")
            var preArchitectureStudies: PreArchitectureStudies,
            @SerializedName("Architecture and Related Services, Other")
            var architectureAndRelatedServicesOther: ArchitectureAndRelatedServicesOther,
            @SerializedName("Communication, Journalism, and Related Programs")
            var communicationJournalismAndRelatedPrograms: CommunicationJournalismAndRelatedPrograms,
            @SerializedName("Communication and Media Studies")
            var communicationAndMediaStudies: CommunicationAndMediaStudies,
            @SerializedName("Speech Communication and Rhetoric")
            var speechCommunicationAndRhetoric: SpeechCommunicationAndRhetoric,
            @SerializedName("Journalism")
            var journalism: Journalism,
            @SerializedName("Journalism, Other")
            var journalismOther: JournalismOther,
            @SerializedName("Radio, Television, and Digital Communication")
            var radioTelevisionAndDigitalCommunication: RadioTelevisionAndDigitalCommunication,
            @SerializedName("Digital Communication and Media/Multimedia")
            var digitalCommunicationAndMediaMultimedia: DigitalCommunicationAndMediaMultimedia,
            @SerializedName("Public Relations, Advertising, and Applied Communication")
            var publicRelationsAdvertisingAndAppliedCommunication: PublicRelationsAdvertisingAndAppliedCommunication,
            @SerializedName("Public Relations, Advertising, and Applied Communication, Other")
            var publicRelationsAdvertisingAndAppliedCommunicationOther: PublicRelationsAdvertisingAndAppliedCommunicationOther,
            @SerializedName("Computer and Information Sciences and Support Services")
            var computerAndInformationSciencesAndSupportServices: ComputerAndInformationSciencesAndSupportServices,
            @SerializedName("Computer and Information Sciences, General")
            var computerAndInformationSciencesGeneral: ComputerAndInformationSciencesGeneral,
            @SerializedName("Information Technology")
            var informationTechnology: InformationTechnology,
            @SerializedName("Computer Science")
            var computerScience: ComputerScience,
            @SerializedName("Education")
            var education: Education,
            @SerializedName("Education, General")
            var educationGeneral: EducationGeneral,
            @SerializedName("Educational Administration and Supervision")
            var educationalAdministrationAndSupervision: EducationalAdministrationAndSupervision,
            @SerializedName("Educational Leadership and Administration, General")
            var educationalLeadershipAndAdministrationGeneral: EducationalLeadershipAndAdministrationGeneral,
            @SerializedName("Higher Education/Higher Education Administration")
            var higherEducationHigherEducationAdministration: HigherEducationHigherEducationAdministration,
            @SerializedName("Educational Administration and Supervision, Other")
            var educationalAdministrationAndSupervisionOther: EducationalAdministrationAndSupervisionOther,
            @SerializedName("Special Education and Teaching")
            var specialEducationAndTeaching: SpecialEducationAndTeaching,
            @SerializedName("Education/Teaching of Individuals in Elementary Special Education Programs")
            var educationTeachingOfIndividualsInElementarySpecialEducationPrograms: EducationTeachingOfIndividualsInElementarySpecialEducationPrograms,
            @SerializedName("Teacher Education and Professional Development, Specific Levels and Methods")
            var teacherEducationAndProfessionalDevelopmentSpecificLevelsAndMethods: TeacherEducationAndProfessionalDevelopmentSpecificLevelsAndMethods,
            @SerializedName("Elementary Education and Teaching")
            var elementaryEducationAndTeaching: ElementaryEducationAndTeaching,
            @SerializedName("Junior High/Intermediate/Middle School Education and Teaching")
            var juniorHighIntermediateMiddleSchoolEducationAndTeaching: JuniorHighIntermediateMiddleSchoolEducationAndTeaching,
            @SerializedName("Teacher Education and Professional Development, Specific Subject Areas")
            var teacherEducationAndProfessionalDevelopmentSpecificSubjectAreas: TeacherEducationAndProfessionalDevelopmentSpecificSubjectAreas,
            @SerializedName("English/Language Arts Teacher Education")
            var englishLanguageArtsTeacherEducation: EnglishLanguageArtsTeacherEducation,
            @SerializedName("Mathematics Teacher Education")
            var mathematicsTeacherEducation: MathematicsTeacherEducation,
            @SerializedName("Music Teacher Education")
            var musicTeacherEducation: MusicTeacherEducation,
            @SerializedName("Science Teacher Education/General Science Teacher Education")
            var scienceTeacherEducationGeneralScienceTeacherEducation: ScienceTeacherEducationGeneralScienceTeacherEducation,
            @SerializedName("Social Studies Teacher Education")
            var socialStudiesTeacherEducation: SocialStudiesTeacherEducation,
            @SerializedName("Computer Teacher Education")
            var computerTeacherEducation: ComputerTeacherEducation,
            @SerializedName("Biology Teacher Education")
            var biologyTeacherEducation: BiologyTeacherEducation,
            @SerializedName("History Teacher Education")
            var historyTeacherEducation: HistoryTeacherEducation,
            @SerializedName("Physics Teacher Education")
            var physicsTeacherEducation: PhysicsTeacherEducation,
            @SerializedName("Spanish Language Teacher Education")
            var spanishLanguageTeacherEducation: SpanishLanguageTeacherEducation,
            @SerializedName("Engineering")
            var engineering: Engineering,
            @SerializedName("Engineering, General")
            var engineeringGeneral: EngineeringGeneral,
            @SerializedName("Foreign Languages, Literatures, and Linguistics")
            var foreignLanguagesLiteraturesAndLinguistics: ForeignLanguagesLiteraturesAndLinguistics,
            @SerializedName("Romance Languages, Literatures, and Linguistics")
            var romanceLanguagesLiteraturesAndLinguistics: RomanceLanguagesLiteraturesAndLinguistics,
            @SerializedName("Spanish Language and Literature")
            var spanishLanguageAndLiterature: SpanishLanguageAndLiterature,
            @SerializedName("Family and Consumer Sciences/Human Sciences")
            var familyAndConsumerSciencesHumanSciences: FamilyAndConsumerSciencesHumanSciences,
            @SerializedName("Human Development, Family Studies, and Related Services")
            var humanDevelopmentFamilyStudiesAndRelatedServices: HumanDevelopmentFamilyStudiesAndRelatedServices,
            @SerializedName("Human Development and Family Studies, General")
            var humanDevelopmentAndFamilyStudiesGeneral: HumanDevelopmentAndFamilyStudiesGeneral,
            @SerializedName("English Language and Literature/Letters")
            var englishLanguageAndLiteratureLetters: EnglishLanguageAndLiteratureLetters,
            @SerializedName("English Language and Literature, General")
            var englishLanguageAndLiteratureGeneral: EnglishLanguageAndLiteratureGeneral,
            @SerializedName("Liberal Arts and Sciences, General Studies and Humanities")
            var liberalArtsAndSciencesGeneralStudiesAndHumanities: LiberalArtsAndSciencesGeneralStudiesAndHumanities,
            @SerializedName("Liberal Arts and Sciences/Liberal Studies")
            var liberalArtsAndSciencesLiberalStudies: LiberalArtsAndSciencesLiberalStudies,
            @SerializedName("Biological and Biomedical Sciences")
            var biologicalAndBiomedicalSciences: BiologicalAndBiomedicalSciences,
            @SerializedName("Biology, General")
            var biologyGeneral: BiologyGeneral,
            @SerializedName("Biology/Biological Sciences, General")
            var biologyBiologicalSciencesGeneral: BiologyBiologicalSciencesGeneral,
            @SerializedName("Biochemistry, Biophysics and Molecular Biology")
            var biochemistryBiophysicsAndMolecularBiology: BiochemistryBiophysicsAndMolecularBiology,
            @SerializedName("Biochemistry")
            var biochemistry: Biochemistry,
            @SerializedName("Mathematics and Statistics")
            var mathematicsAndStatistics: MathematicsAndStatistics,
            @SerializedName("Mathematics")
            var mathematics: Mathematics,
            @SerializedName("Mathematics, General")
            var mathematicsGeneral: MathematicsGeneral,
            @SerializedName("Multi/Interdisciplinary Studies")
            var multiInterdisciplinaryStudies: MultiInterdisciplinaryStudies,
            @SerializedName("Peace Studies and Conflict Resolution")
            var peaceStudiesAndConflictResolution: PeaceStudiesAndConflictResolution,
            @SerializedName("Gerontology")
            var gerontology: Gerontology,
            @SerializedName("International/Globalization Studies")
            var internationalGlobalizationStudies: InternationalGlobalizationStudies,
            @SerializedName("Multi/Interdisciplinary Studies, Other")
            var multiInterdisciplinaryStudiesOther: MultiInterdisciplinaryStudiesOther,
//            @SerializedName("Multi-/Interdisciplinary Studies, Other")
//            var multiInterdisciplinaryStudiesOther: MultiInterdisciplinaryStudiesOther,
            @SerializedName("Parks, Recreation, Leisure, Fitness, and Kinesiology")
            var parksRecreationLeisureFitnessAndKinesiology: ParksRecreationLeisureFitnessAndKinesiology,
            @SerializedName("Sports, Kinesiology, and Physical Education/Fitness")
            var sportsKinesiologyAndPhysicalEducationFitness: SportsKinesiologyAndPhysicalEducationFitness,
            @SerializedName("Sport and Fitness Administration/Management")
            var sportAndFitnessAdministrationManagement: SportAndFitnessAdministrationManagement,
            @SerializedName("Exercise Science and Kinesiology")
            var exerciseScienceAndKinesiology: ExerciseScienceAndKinesiology,
            @SerializedName("Philosophy and Religious Studies")
            var philosophyAndReligiousStudies: PhilosophyAndReligiousStudies,
            @SerializedName("Religion/Religious Studies")
            var religionReligiousStudies: ReligionReligiousStudies,
            @SerializedName("Theology and Religious Vocations")
            var theologyAndReligiousVocations: TheologyAndReligiousVocations,
            @SerializedName("Bible/Biblical Studies")
            var bibleBiblicalStudies: BibleBiblicalStudies,
            @SerializedName("Missions/Missionary Studies and Missiology")
            var missionsMissionaryStudiesAndMissiology: MissionsMissionaryStudiesAndMissiology,
            @SerializedName("Missions/Missionary Studies")
            var missionsMissionaryStudies: MissionsMissionaryStudies,
            @SerializedName("Theological and Ministerial Studies")
            var theologicalAndMinisterialStudies: TheologicalAndMinisterialStudies,
            @SerializedName("Theology/Theological Studies")
            var theologyTheologicalStudies: TheologyTheologicalStudies,
            @SerializedName("Divinity/Ministry")
            var divinityMinistry: DivinityMinistry,
            @SerializedName("Pastoral Counseling and Specialized Ministries")
            var pastoralCounselingAndSpecializedMinistries: PastoralCounselingAndSpecializedMinistries,
            @SerializedName("Pastoral Counseling and Specialized Ministries, Other")
            var pastoralCounselingAndSpecializedMinistriesOther: PastoralCounselingAndSpecializedMinistriesOther,
            @SerializedName("Theology and Religious Vocations, Other")
            var theologyAndReligiousVocationsOther: TheologyAndReligiousVocationsOther,
            @SerializedName("Physical Sciences")
            var physicalSciences: PhysicalSciences,
            @SerializedName("Chemistry")
            var chemistry: Chemistry,
            @SerializedName("Chemistry, General")
            var chemistryGeneral: ChemistryGeneral,
            @SerializedName("Physics")
            var physics: Physics,
            @SerializedName("Physics, General")
            var physicsGeneral: PhysicsGeneral,
            @SerializedName("Psychology")
            var psychology: Psychology,
            @SerializedName("Psychology, General")
            var psychologyGeneral: PsychologyGeneral,
            @SerializedName("Clinical, Counseling and Applied Psychology")
            var clinicalCounselingAndAppliedPsychology: ClinicalCounselingAndAppliedPsychology,
            @SerializedName("Clinical Psychology")
            var clinicalPsychology: ClinicalPsychology,
            @SerializedName("Counseling Psychology")
            var counselingPsychology: CounselingPsychology,
            @SerializedName("School Psychology")
            var schoolPsychology: SchoolPsychology,
            @SerializedName("Homeland Security, Law Enforcement, Firefighting and Related Protective Services")
            var homelandSecurityLawEnforcementFirefightingAndRelatedProtectiveServices: HomelandSecurityLawEnforcementFirefightingAndRelatedProtectiveServices,
            @SerializedName("Criminal Justice and Corrections")
            var criminalJusticeAndCorrections: CriminalJusticeAndCorrections,
            @SerializedName("Criminal Justice/Law Enforcement Administration")
            var criminalJusticeLawEnforcementAdministration: CriminalJusticeLawEnforcementAdministration,
            @SerializedName("Public Administration and Social Service Professions")
            var publicAdministrationAndSocialServiceProfessions: PublicAdministrationAndSocialServiceProfessions,
            @SerializedName("Social Work")
            var socialWork: SocialWork,
            @SerializedName("Social Sciences")
            var socialSciences: SocialSciences,
            @SerializedName("Political Science and Government")
            var politicalScienceAndGovernment: PoliticalScienceAndGovernment,
            @SerializedName("Political Science and Government, General")
            var politicalScienceAndGovernmentGeneral: PoliticalScienceAndGovernmentGeneral,
            @SerializedName("Sociology")
            var sociology: Sociology,
            @SerializedName("Sociology, General")
            var sociologyGeneral: SociologyGeneral,
            @SerializedName("Visual and Performing Arts")
            var visualAndPerformingArts: VisualAndPerformingArts,
            @SerializedName("Design and Applied Arts")
            var designAndAppliedArts: DesignAndAppliedArts,
            @SerializedName("Interior Design")
            var interiorDesign: InteriorDesign,
            @SerializedName("Graphic Design")
            var graphicDesign: GraphicDesign,
            @SerializedName("Game and Interactive Media Design")
            var gameAndInteractiveMediaDesign: GameAndInteractiveMediaDesign,
            @SerializedName("Drama/Theatre Arts and Stagecraft")
            var dramaTheatreArtsAndStagecraft: DramaTheatreArtsAndStagecraft,
            @SerializedName("Drama and Dramatics/Theatre Arts, General")
            var dramaAndDramaticsTheatreArtsGeneral: DramaAndDramaticsTheatreArtsGeneral,
            @SerializedName("Fine and Studio Arts")
            var fineAndStudioArts: FineAndStudioArts,
            @SerializedName("Fine/Studio Arts, General")
            var fineStudioArtsGeneral: FineStudioArtsGeneral,
            @SerializedName("Music")
            var music: Music,
            @SerializedName("Music, General")
            var musicGeneral: MusicGeneral,
            @SerializedName("Voice and Opera")
            var voiceAndOpera: VoiceAndOpera,
            @SerializedName("Health Professions and Related Programs")
            var healthProfessionsAndRelatedPrograms: HealthProfessionsAndRelatedPrograms,
            @SerializedName("Communication Disorders Sciences and Services")
            var communicationDisordersSciencesAndServices: CommunicationDisordersSciencesAndServices,
            @SerializedName("Speech-Language Pathology/Pathologist")
            var speechLanguagePathologyPathologist: SpeechLanguagePathologyPathologist,
            @SerializedName("Health and Medical Administrative Services")
            var healthAndMedicalAdministrativeServices: HealthAndMedicalAdministrativeServices,
            @SerializedName("Health/Health Care Administration/Management")
            var healthHealthCareAdministrationManagement: HealthHealthCareAdministrationManagement,
            @SerializedName("Allied Health Diagnostic, Intervention, and Treatment Professions")
            var alliedHealthDiagnosticInterventionAndTreatmentProfessions: AlliedHealthDiagnosticInterventionAndTreatmentProfessions,
            @SerializedName("Athletic Training/Trainer")
            var athleticTrainingTrainer: AthleticTrainingTrainer,
            @SerializedName("Health/Medical Preparatory Programs")
            var healthMedicalPreparatoryPrograms: HealthMedicalPreparatoryPrograms,
            @SerializedName("Health/Medical Preparatory Programs, Other")
            var healthMedicalPreparatoryProgramsOther: HealthMedicalPreparatoryProgramsOther,
            @SerializedName("Mental and Social Health Services and Allied Professions")
            var mentalAndSocialHealthServicesAndAlliedProfessions: MentalAndSocialHealthServicesAndAlliedProfessions,
            @SerializedName("Marriage and Family Therapy/Counseling")
            var marriageAndFamilyTherapyCounseling: MarriageAndFamilyTherapyCounseling,
            @SerializedName("Clinical Pastoral Counseling/Patient Counseling")
            var clinicalPastoralCounselingPatientCounseling: ClinicalPastoralCounselingPatientCounseling,
            @SerializedName("Rehabilitation and Therapeutic Professions")
            var rehabilitationAndTherapeuticProfessions: RehabilitationAndTherapeuticProfessions,
            @SerializedName("Occupational Therapy/Therapist")
            var occupationalTherapyTherapist: OccupationalTherapyTherapist,
            @SerializedName("Dietetics and Clinical Nutrition Services")
            var dieteticsAndClinicalNutritionServices: DieteticsAndClinicalNutritionServices,
            @SerializedName("Dietetics/Dietitian")
            var dieteticsDietitian: DieteticsDietitian,
            @SerializedName("Dietetics and Clinical Nutrition Services, Other")
            var dieteticsAndClinicalNutritionServicesOther: DieteticsAndClinicalNutritionServicesOther,
            @SerializedName("Registered Nursing, Nursing Administration, Nursing Research and Clinical Nursing")
            var registeredNursingNursingAdministrationNursingResearchAndClinicalNursing: RegisteredNursingNursingAdministrationNursingResearchAndClinicalNursing,
            @SerializedName("Registered Nursing/Registered Nurse")
            var registeredNursingRegisteredNurse: RegisteredNursingRegisteredNurse,
            @SerializedName("Nursing Practice")
            var nursingPractice: NursingPractice,
            @SerializedName("Business, Management, Marketing, and Related Support Services")
            var businessManagementMarketingAndRelatedSupportServices: BusinessManagementMarketingAndRelatedSupportServices,
            @SerializedName("Business Administration, Management and Operations")
            var businessAdministrationManagementAndOperations: BusinessAdministrationManagementAndOperations,
            @SerializedName("Business Administration and Management, General")
            var businessAdministrationAndManagementGeneral: BusinessAdministrationAndManagementGeneral,
            @SerializedName("Non-Profit/Public/Organizational Management")
            var nonProfitPublicOrganizationalManagement: NonProfitPublicOrganizationalManagement,
            @SerializedName("Accounting and Related Services")
            var accountingAndRelatedServices: AccountingAndRelatedServices,
            @SerializedName("Accounting")
            var accounting: Accounting,
            @SerializedName("Finance and Financial Management Services")
            var financeAndFinancialManagementServices: FinanceAndFinancialManagementServices,
            @SerializedName("Finance, General")
            var financeGeneral: FinanceGeneral,
            @SerializedName("Management Information Systems and Services")
            var managementInformationSystemsAndServices: ManagementInformationSystemsAndServices,
            @SerializedName("Management Information Systems, General")
            var managementInformationSystemsGeneral: ManagementInformationSystemsGeneral,
            @SerializedName("Information Resources Management")
            var informationResourcesManagement: InformationResourcesManagement,
            @SerializedName("Marketing")
            var marketing: Marketing,
            @SerializedName("Marketing/Marketing Management, General")
            var marketingMarketingManagementGeneral: MarketingMarketingManagementGeneral,
            @SerializedName("History")
            var history: History,
            @SerializedName("History, General")
            var historyGeneral: HistoryGeneral
        ) {
            data class AgriculturalAnimalPlantVeterinaryScienceAndRelatedFields(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class AgriculturalBusinessAndManagement(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class AgribusinessAgriculturalBusinessOperations(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class AnimalSciences(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class AnimalSciencesGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class NaturalResourcesAndConservation(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class NaturalResourcesConservationAndResearch(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class EnvironmentalScience(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class ArchitectureAndRelatedServices(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class Architecture(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class PreArchitectureStudies(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: Any
            )

            data class ArchitectureAndRelatedServicesOther(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class CommunicationJournalismAndRelatedPrograms(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class CommunicationAndMediaStudies(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class SpeechCommunicationAndRhetoric(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class Journalism(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class JournalismOther(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class RadioTelevisionAndDigitalCommunication(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class DigitalCommunicationAndMediaMultimedia(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class PublicRelationsAdvertisingAndAppliedCommunication(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class PublicRelationsAdvertisingAndAppliedCommunicationOther(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class ComputerAndInformationSciencesAndSupportServices(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class ComputerAndInformationSciencesGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class InformationTechnology(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class ComputerScience(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class Education(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class EducationGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class EducationalAdministrationAndSupervision(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class EducationalLeadershipAndAdministrationGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class HigherEducationHigherEducationAdministration(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class EducationalAdministrationAndSupervisionOther(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class SpecialEducationAndTeaching(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class EducationTeachingOfIndividualsInElementarySpecialEducationPrograms(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class TeacherEducationAndProfessionalDevelopmentSpecificLevelsAndMethods(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class ElementaryEducationAndTeaching(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class JuniorHighIntermediateMiddleSchoolEducationAndTeaching(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class TeacherEducationAndProfessionalDevelopmentSpecificSubjectAreas(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class EnglishLanguageArtsTeacherEducation(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class MathematicsTeacherEducation(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class MusicTeacherEducation(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class ScienceTeacherEducationGeneralScienceTeacherEducation(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class SocialStudiesTeacherEducation(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class ComputerTeacherEducation(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class BiologyTeacherEducation(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class HistoryTeacherEducation(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class PhysicsTeacherEducation(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class SpanishLanguageTeacherEducation(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class Engineering(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class EngineeringGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class ForeignLanguagesLiteraturesAndLinguistics(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class RomanceLanguagesLiteraturesAndLinguistics(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class SpanishLanguageAndLiterature(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class FamilyAndConsumerSciencesHumanSciences(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class HumanDevelopmentFamilyStudiesAndRelatedServices(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class HumanDevelopmentAndFamilyStudiesGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class EnglishLanguageAndLiteratureLetters(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class EnglishLanguageAndLiteratureGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class LiberalArtsAndSciencesGeneralStudiesAndHumanities(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class LiberalArtsAndSciencesLiberalStudies(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class BiologicalAndBiomedicalSciences(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class BiologyGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class BiologyBiologicalSciencesGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class BiochemistryBiophysicsAndMolecularBiology(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class Biochemistry(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class MathematicsAndStatistics(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class Mathematics(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class MathematicsGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class MultiInterdisciplinaryStudies(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class PeaceStudiesAndConflictResolution(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class Gerontology(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class InternationalGlobalizationStudies(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class MultiInterdisciplinaryStudiesOther(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

//            data class MultiInterdisciplinaryStudiesOther(
//                @SerializedName("Associate Degree")
//                var associateDegree: Int,
//                @SerializedName("Master Degree")
//                var masterDegree: Int,
//                @SerializedName("Bachelor Degree")
//                var bachelorDegree: Int,
//                @SerializedName("Doctorate Degree")
//                var doctorateDegree: Int,
//                @SerializedName("count")
//                var count: Int,
//                @SerializedName("description")
//                var description: String
//            )

            data class ParksRecreationLeisureFitnessAndKinesiology(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class SportsKinesiologyAndPhysicalEducationFitness(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class SportAndFitnessAdministrationManagement(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class ExerciseScienceAndKinesiology(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class PhilosophyAndReligiousStudies(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class ReligionReligiousStudies(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class TheologyAndReligiousVocations(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class BibleBiblicalStudies(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class MissionsMissionaryStudiesAndMissiology(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class MissionsMissionaryStudies(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class TheologicalAndMinisterialStudies(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class TheologyTheologicalStudies(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class DivinityMinistry(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class PastoralCounselingAndSpecializedMinistries(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class PastoralCounselingAndSpecializedMinistriesOther(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class TheologyAndReligiousVocationsOther(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class PhysicalSciences(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class Chemistry(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class ChemistryGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class Physics(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class PhysicsGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class Psychology(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class PsychologyGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class ClinicalCounselingAndAppliedPsychology(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class ClinicalPsychology(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class CounselingPsychology(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class SchoolPsychology(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class HomelandSecurityLawEnforcementFirefightingAndRelatedProtectiveServices(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class CriminalJusticeAndCorrections(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class CriminalJusticeLawEnforcementAdministration(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class PublicAdministrationAndSocialServiceProfessions(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class SocialWork(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class SocialSciences(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class PoliticalScienceAndGovernment(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class PoliticalScienceAndGovernmentGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class Sociology(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class SociologyGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class VisualAndPerformingArts(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class DesignAndAppliedArts(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class InteriorDesign(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class GraphicDesign(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class GameAndInteractiveMediaDesign(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class DramaTheatreArtsAndStagecraft(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class DramaAndDramaticsTheatreArtsGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class FineAndStudioArts(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class FineStudioArtsGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class Music(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class MusicGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class VoiceAndOpera(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class HealthProfessionsAndRelatedPrograms(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class CommunicationDisordersSciencesAndServices(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class SpeechLanguagePathologyPathologist(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class HealthAndMedicalAdministrativeServices(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class HealthHealthCareAdministrationManagement(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class AlliedHealthDiagnosticInterventionAndTreatmentProfessions(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class AthleticTrainingTrainer(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class HealthMedicalPreparatoryPrograms(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class HealthMedicalPreparatoryProgramsOther(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class MentalAndSocialHealthServicesAndAlliedProfessions(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class MarriageAndFamilyTherapyCounseling(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class ClinicalPastoralCounselingPatientCounseling(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class RehabilitationAndTherapeuticProfessions(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class OccupationalTherapyTherapist(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class DieteticsAndClinicalNutritionServices(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class DieteticsDietitian(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class DieteticsAndClinicalNutritionServicesOther(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class RegisteredNursingNursingAdministrationNursingResearchAndClinicalNursing(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class RegisteredNursingRegisteredNurse(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class NursingPractice(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class BusinessManagementMarketingAndRelatedSupportServices(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class BusinessAdministrationManagementAndOperations(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class BusinessAdministrationAndManagementGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class NonProfitPublicOrganizationalManagement(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class AccountingAndRelatedServices(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class Accounting(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class FinanceAndFinancialManagementServices(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class FinanceGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class ManagementInformationSystemsAndServices(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class ManagementInformationSystemsGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class InformationResourcesManagement(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class Marketing(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class MarketingMarketingManagementGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class History(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )

            data class HistoryGeneral(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )
        }
    }

    data class VarsityAthleticTeams(
        @SerializedName("Baseball")
        var baseball: Baseball,
        @SerializedName("Basketball")
        var basketball: Basketball,
        @SerializedName("All Track Combined")
        var allTrackCombined: AllTrackCombined,
        @SerializedName("Football")
        var football: Football,
        @SerializedName("Golf")
        var golf: Golf,
        @SerializedName("Soccer")
        var soccer: Soccer,
        @SerializedName("Softball")
        var softball: Softball,
        @SerializedName("Tennis")
        var tennis: Tennis,
        @SerializedName("Volleyball")
        var volleyball: Volleyball
    ) {
        data class Baseball(
            @SerializedName("men")
            var men: Int,
            @SerializedName("women")
            var women: Int
        )

        data class Basketball(
            @SerializedName("men")
            var men: Int,
            @SerializedName("women")
            var women: Int
        )

        data class AllTrackCombined(
            @SerializedName("men")
            var men: Int,
            @SerializedName("women")
            var women: Int
        )

        data class Football(
            @SerializedName("men")
            var men: Int,
            @SerializedName("women")
            var women: Int
        )

        data class Golf(
            @SerializedName("men")
            var men: Int,
            @SerializedName("women")
            var women: Int
        )

        data class Soccer(
            @SerializedName("men")
            var men: Int,
            @SerializedName("women")
            var women: Int
        )

        data class Softball(
            @SerializedName("men")
            var men: Int,
            @SerializedName("women")
            var women: Int
        )

        data class Tennis(
            @SerializedName("men")
            var men: Int,
            @SerializedName("women")
            var women: Int
        )

        data class Volleyball(
            @SerializedName("men")
            var men: Int,
            @SerializedName("women")
            var women: Int
        )
    }



    data class VarsityAthleticSports(
        @SerializedName("teams")
        var teams: Teams,
        @SerializedName("athletic_association")
        var athleticAssociation: String
    ) {
        data class Teams(
            @SerializedName("Baseball")
            var baseball: Baseball,
            @SerializedName("Basketball")
            var basketball: Basketball,
            @SerializedName("All Track Combined")
            var allTrackCombined: AllTrackCombined,
            @SerializedName("Football")
            var football: Football,
            @SerializedName("Golf")
            var golf: Golf,
            @SerializedName("Soccer")
            var soccer: Soccer,
            @SerializedName("Softball")
            var softball: Softball,
            @SerializedName("Tennis")
            var tennis: Tennis,
            @SerializedName("Volleyball")
            var volleyball: Volleyball,
            @SerializedName("Beach Volleyball")
            var beachVolleyball: BeachVolleyball
        ) {
            data class Baseball(
                @SerializedName("men")
                var men: String,
                @SerializedName("women")
                var women: Int
            )

            data class Basketball(
                @SerializedName("men")
                var men: String,
                @SerializedName("women")
                var women: String
            )

            data class AllTrackCombined(
                @SerializedName("men")
                var men: String,
                @SerializedName("women")
                var women: String
            )

            data class Football(
                @SerializedName("men")
                var men: String,
                @SerializedName("women")
                var women: Int
            )

            data class Golf(
                @SerializedName("men")
                var men: String,
                @SerializedName("women")
                var women: Int
            )

            data class Soccer(
                @SerializedName("men")
                var men: Int,
                @SerializedName("women")
                var women: String
            )

            data class Softball(
                @SerializedName("men")
                var men: Int,
                @SerializedName("women")
                var women: String
            )

            data class Tennis(
                @SerializedName("men")
                var men: String,
                @SerializedName("women")
                var women: String
            )

            data class Volleyball(
                @SerializedName("men")
                var men: Int,
                @SerializedName("women")
                var women: String
            )

            data class BeachVolleyball(
                @SerializedName("men")
                var men: Int,
                @SerializedName("women")
                var women: String
            )
        }
    }

    data class Transfers(
        @SerializedName("transfer_accepted")
        var transferAccepted: Int,
        @SerializedName("enrolled_accepted")
        var enrolledAccepted: Int,
        @SerializedName("transfer_application_deadline")
        var transferApplicationDeadline: TransferApplicationDeadline,
        @SerializedName("transfer_reqirements")
        var transferReqirements: TransferReqirements,
        @SerializedName("tranfer_credits")
        var tranferCredits: TranferCredits,
        @SerializedName("application_deadline")
        var applicationDeadline: String
    ) {
        data class TransferApplicationDeadline(
            @SerializedName("fall")
            var fall: String,
            @SerializedName("winter")
            var winter: String,
            @SerializedName("spring")
            var spring: String,
            @SerializedName("summer")
            var summer: String
        )

        data class TransferReqirements(
            @SerializedName("college_transcripts")
            var collegeTranscripts: String,
            @SerializedName("essay_test")
            var essayTest: String,
            @SerializedName("minimum")
            var minimum: String?
        )

        data class TranferCredits(
            @SerializedName("max_transfered")
            var maxTransfered: Int,
            @SerializedName("max_transfered_must")
            var maxTransferedMust: Int
        )
    }

    data class Services(
        @SerializedName("remedial_services")
        var remedialServices: List<String>,
        @SerializedName("counseling_services")
        var counselingServices: CounselingServices,
        @SerializedName("career_services")
        var careerServices: CareerServices,
        @SerializedName("additional_services")
        var additionalServices: AdditionalServices,
        @SerializedName("special_programs")
        var specialPrograms: SpecialPrograms,
        @SerializedName("campus_accessible")
        var campusAccessible: CampusAccessible
    ) {
        data class CounselingServices(
            @SerializedName("0")
            var x0: String,
            @SerializedName("1")
            var x1: String,
            @SerializedName("2")
            var x2: String,
            @SerializedName("3")
            var x3: String,
            @SerializedName("4")
            var x4: String,
            @SerializedName("5")
            var x5: String,
            @SerializedName("6")
            var x6: String,
            @SerializedName("7")
            var x7: String,
            @SerializedName("other_counseling")
            var otherCounseling: String
        )

        data class CareerServices(
            @SerializedName("0")
            var x0: String,
            @SerializedName("1")
            var x1: String,
            @SerializedName("2")
            var x2: String,
            @SerializedName("3")
            var x3: String,
            @SerializedName("4")
            var x4: String,
            @SerializedName("5")
            var x5: String,
            @SerializedName("6")
            var x6: String,
            @SerializedName("other_placement")
            var otherPlacement: Any
        )

        data class AdditionalServices(
            @SerializedName("0")
            var x0: String,
            @SerializedName("1")
            var x1: String,
            @SerializedName("2")
            var x2: String,
            @SerializedName("3")
            var x3: String,
            @SerializedName("other_additional")
            var otherAdditional: Any
        )

        data class SpecialPrograms(
            @SerializedName("0")
            var x0: String,
            @SerializedName("1")
            var x1: String,
            @SerializedName("2")
            var x2: String,
            @SerializedName("3")
            var x3: String,
            @SerializedName("4")
            var x4: String,
            @SerializedName("5")
            var x5: String,
            @SerializedName("6")
            var x6: String,
            @SerializedName("7")
            var x7: String,
            @SerializedName("other_handicapped")
            var otherHandicapped: String
        )

        data class CampusAccessible(
            @SerializedName("percent")
            var percent: String
        )
    }

    data class DegreeMajors1(
        @SerializedName("majors")
        var majors: ArrayList<Majors1>?,
        @SerializedName("program_offered")
        var programOffered: List<String>
    ) {
        data class Majors1(
            @SerializedName("key")
            var name: String,
            @SerializedName("values")
            var agriculturalBusinessAndManagement: AnimalSciences
        ) {
            data class AnimalSciences(
                @SerializedName("Associate Degree")
                var associateDegree: Int,
                @SerializedName("Master Degree")
                var masterDegree: Int,
                @SerializedName("Bachelor Degree")
                var bachelorDegree: Int,
                @SerializedName("Doctorate Degree")
                var doctorateDegree: Int,
                @SerializedName("count")
                var count: Int,
                @SerializedName("description")
                var description: String
            )
        }
    }
    data class VarsityAthleticSports1(
        @SerializedName("teams")
        var teams: ArrayList<Teams1>?,
        @SerializedName("athletic_association")
        var athleticAssociation: String
    ) {
        data class Teams1(
            @SerializedName("key")
            var name: String,
            @SerializedName("values")
            var values: Baseball
        ) {
            data class Baseball(
                @SerializedName("men")
                var men: String,
                @SerializedName("women")
                var women: String
            )
        }
    }
}

