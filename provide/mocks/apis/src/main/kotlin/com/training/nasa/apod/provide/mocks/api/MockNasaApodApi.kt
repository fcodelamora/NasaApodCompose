package com.training.nasa.apod.provide.mocks.api

import com.training.nasa.apod.core.api.INasaApodApi
import com.training.nasa.apod.core.api.requests.GetPictureRequestParams
import com.training.nasa.apod.core.api.requests.GetPicturesByDateRangeRequestParams
import com.training.nasa.apod.core.api.response.GetPictureResponse
import com.training.nasa.apod.core.entities.NasaServer.nasaApodFirstEntry
import com.training.nasa.apod.provide.mocks.api.debugflags.IMockDebugFlagsRepository
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber
import timber.log.debug

@Singleton
class MockNasaApodApi @Inject constructor(private val mockDebugFlagsRepository: IMockDebugFlagsRepository) :
    INasaApodApi {

    override suspend fun getPictureOfTheDay(requestParams: GetPictureRequestParams): GetPictureResponse {
        mockDebugFlagsRepository.handleError()
        mockDebugFlagsRepository.loadMockDebugFlags().run {
            return GetPictureResponse(
                date = requestParams.date,
                explanation = "Grand design spiral galaxy Messier 99 looks majestic on a truly cosmic scale. " +
                    "This recently processed full galaxy portrait stretches over 70,000 light-years across M99. " +
                    "The sharp view is a combination of ultraviolet, visible, and infrared image data from the " +
                    "Hubble Space Telescope. About 50 million light-years distant toward the well-groomed " +
                    "constellation Coma Bernices, the face-on spiral is a member of the nearby Virgo Galaxy Cluster. " +
                    "Also cataloged as NGC 4254, a close encounter with another Virgo cluster member has likely " +
                    "influenced the shape of its well-defined, blue spiral arms.",
                hdUrl = "https://apod.nasa.gov/apod/image/2106/M99_LeoShatz_cropped.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "Messier 99",
                url = "https://apod.nasa.gov/apod/image/2106/M99_LeoShatz_cropped1024.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ).also {
                Timber.debug { it.toString() }
            }
        }
    }

    override suspend fun getPictureOfTheDayByDateRange(requestParams: GetPicturesByDateRangeRequestParams):
        List<GetPictureResponse> {
        mockDebugFlagsRepository.handleError()

        val startDate = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).parse(requestParams.startDate)
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val now = LocalDate.now()
        val isCurrentMonth = (startDate.year == now.year) && (startDate.month == now.month)

        val firstEntry = nasaApodFirstEntry
        val isNasaApodFirstEntryMonth =
            (startDate.year == firstEntry.year) && (startDate.month == firstEntry.month)

        val daysInMonth = startDate.lengthOfMonth()

        val dateAsArray = requestParams.endDate.split("-")
        val requestYearMonth = dateAsArray[0] + "-" + dateAsArray[1]
        mockDebugFlagsRepository.loadMockDebugFlags().run {

            val response = getMonthAllDaysResponse(requestYearMonth, daysInMonth)

            // Remove days before Nasa APODs first entry
            if (isNasaApodFirstEntryMonth) {
                val firstEntryDay = firstEntry.dayOfMonth
                val filteredResponse = response.filter { picResponse ->
                    val pictureDay = picResponse.date.split("-")[2].toInt()
                    pictureDay >= firstEntryDay
                }
                response.clear()
                response.addAll(filteredResponse)
            }

            // Remove future days for current month
            if (isCurrentMonth) {
                val searchRangeLastDay = dateAsArray[2].toInt()
                val filteredResponse = response.filter { picResponse ->
                    val pictureDay = picResponse.date.split("-")[2].toInt()
                    pictureDay <= searchRangeLastDay
                }
                response.clear()
                response.addAll(filteredResponse)
            }

            return response.also {
                Timber.debug { it.toString() }
            }
        }
    }

    private fun getMonthAllDaysResponse(
        requestYearMonth: String,
        daysInMonth: Int
    ): MutableList<GetPictureResponse> {

        val mockText = "ipsum dolor sit amet, consectetur adipiscing elit. Nunc eget libero, " +
            "laoreet urna eu, fermentum urna. Sed sodales mauris purus, in aliquet diam consectetur in. " +
            "Vestibulum orci elit, egestas quis nunc interdum, maximus iaculis tellus. Aenean tincidunt " +
            "turpis nisl, a ornare augue semper at. Integer sagittis sollicitudin justo vel malesuada. " +
            "Sed non finibus lectus. Etiam et porttitor elit. Nunc sit amet tincidunt risus. " +
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam tincidunt porttitor nulla, " +
            "eu maximus metus pellentesque vitae. Duis nec tristique augue, id commodo elit. " +
            "Praesent vitae aliquet elit. Curabitur pulvinar porttitor dui, " +
            "fermentum pulvinar neque consequat in."

        val response = mutableListOf(
            GetPictureResponse(
                date = "$requestYearMonth-01",
                explanation = "Lorem 01 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/starless_color.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "NGC 281: Starless with Stars",
                url = "https://apod.nasa.gov/apod/image/2111/starless_color_1024.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            // Long article contents mock
            GetPictureResponse(
                date = "$requestYearMonth-02",
                explanation = "Lorem 02 ipsum dolor sit amet, consectetur adipiscing elit. Nunc eget libero, " +
                    "laoreet urna eu, fermentum urna. Sed sodales mauris purus, in aliquet diam consectetur in. " +
                    "Vestibulum orci elit, egestas quis nunc interdum, maximus iaculis tellus. Aenean tincidunt " +
                    "turpis nisl, a ornare augue semper at. Integer sagittis sollicitudin justo vel malesuada. Sed " +
                    "non finibus lectus. Etiam et porttitor elit. Nunc sit amet tincidunt risus. Lorem ipsum dolor " +
                    "sit amet, consectetur adipiscing elit. Aliquam tincidunt porttitor nulla, eu maximus metus " +
                    "pellentesque vitae. Duis nec tristique augue, id commodo elit. Praesent vitae aliquet elit. " +
                    "Curabitur pulvinar porttitor dui, fermentum pulvinar neque consequat in. Duis nec tristique " +
                    "augue, id commodo elit. Praesent vitae aliquet elit. Curabitur pulvinar porttitor dui, " +
                    "fermentum pulvinar neque consequat in. Duis nec tristique augue, id commodo elit. Praesent " +
                    "vitae aliquet elit. Curabitur pulvinar porttitor dui, fermentum pulvinar neque consequat in. " +
                    "Duis nec tristique augue, id commodo elit. Praesent vitae aliquet elit. Curabitur pulvinar " +
                    "porttitor dui, fermentum pulvinar neque consequat in. Duis nec tristique augue, id commodo. " +
                    "Praesent vitae aliquet elit. Curabitur pulvinar porttitor dui, fermentum pulvinar neque " +
                    "consequat in. Duis nec tristique augue, id commodo elit. Praesent vitae aliquet elit. " +
                    "Curabitur pulvinar porttitor dui, fermentum pulvinar neque consequat in. " +
                    "Duis nec tristique augue, id commodo elit. Praesent vitae aliquet elit. " +
                    "Curabitur pulvinar porttitor dui, fermentum pulvinar neque consequat in. " +
                    "Duis nec tristique augue, id commodo elit. Praesent vitae aliquet elit. " +
                    "Curabitur pulvinar porttitor dui, fermentum pulvinar neque consequat in. Duis nec tristique " +
                    "augue, id commodo elit. Praesent vitae aliquet elit. Curabitur pulvinar porttitor dui, " +
                    "fermentum pulvinar neque consequat in. Duis nec tristique augue, id commodo elit. Praesent " +
                    "vitae aliquet elit. Curabitur pulvinar porttitor dui, fermentum pulvinar neque consequat in. " +
                    "Duis nec tristique augue, id commodo elit. Praesent vitae aliquet elit. Curabitur pulvinar " +
                    "porttitor dui, fermentum pulvinar neque consequat in. Duis nec tristique augue, id commodo " +
                    "elit. Praesent vitae aliquet elit. Curabitur pulvinar porttitor dui, fermentum pulvinar neque " +
                    "consequat in. Duis nec tristique augue, id commodo elit. Praesent vitae aliquet elit. Curabitur " +
                    "pulvinar porttitor dui, fermentum pulvinar neque consequat in. Duis nec tristique augue, " +
                    "id commodo elit. Praesent vitae aliquet elit. Curabitur pulvinar porttitor dui, fermentum " +
                    "pulvinar neque consequat in. Duis nec tristique augue, id commodo elit. Praesent vitae " +
                    "aliquet elit. Curabitur pulvinar porttitor dui, fermentum pulvinar neque consequat in. " +
                    "Duis nec tristique augue, id commodo elit. Praesent vitae aliquet elit. Curabitur pulvinar " +
                    "porttitor dui, fermentum pulvinar neque consequat in. Duis nec tristique augue, id commodo " +
                    "elit. Praesent vitae aliquet elit. Curabitur pulvinar porttitor dui, fermentum pulvinar " +
                    "neque consequat in. Duis nec tristique augue, id commodo elit. Praesent vitae aliquet elit. " +
                    "Curabitur pulvinar porttitor dui, fermentum pulvinar neque consequat in. Duis nec tristique " +
                    "augue, id commodo elit. Praesent vitae aliquet elit. Curabitur pulvinar porttitor dui, " +
                    "fermentum pulvinar neque consequat in.",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/MACSJ0138_Hubble_1762.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "SN Requiem: A Supernova Seen Three Times So Far",
                url = "https://apod.nasa.gov/apod/image/2111/MACSJ0138_Hubble_1080.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            // Short article contents mock
            GetPictureResponse(
                date = "$requestYearMonth-03",
                explanation = "Lorem 03 ipsum dolor sit amet, consectetur adipiscing elit. Nunc eget libero faucibus",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/HorseFlame_Ayoub_4305.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "The Horsehead and Flame Nebulas",
                url = "https://apod.nasa.gov/apod/image/2111/HorseFlame_Ayoub_960.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-04",
                explanation = "Lorem 04 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/NGC147NGC185satellites.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "NGC 147 and NGC 185",
                url = "https://apod.nasa.gov/apod/image/2111/NGC147NGC185satellites1024.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-05",
                explanation = "Lorem 05 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/b150_avitabile.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "The Dark Seahorse in Cepheus",
                url = "https://apod.nasa.gov/apod/image/2111/b150_avitabile1092c.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),

            // Skip 06 Intentionally

            GetPictureResponse(
                date = "$requestYearMonth-07",
                explanation = "Grand design spiral galaxy Messier 99 looks majestic on a truly cosmic scale. " +
                    "This recently processed full galaxy portrait stretches over 70,000 light-years across M99. " +
                    "The sharp view is a combination of ultraviolet, visible, and infrared image data from the " +
                    "Hubble Space Telescope. About 50 million light-years distant toward the well-groomed " +
                    "constellation Coma Bernices, the face-on spiral is a member of the nearby Virgo Galaxy " +
                    "Cluster. Also cataloged as NGC 4254, a close encounter with another Virgo cluster member " +
                    "has likely influenced the shape of its well-defined, blue spiral arms.",
                hdUrl = "https://apod.nasa.gov/apod/image/2106/M99_LeoShatz_cropped.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "Messier 99",
                url = "https://apod.nasa.gov/apod/image/2106/M99_LeoShatz_cropped1024.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-08",
                explanation = "Fusce 08 $mockText",
                hdUrl = null,
                mediaType = "video",
                serviceVersion = "v1",
                title = "A Filament Leaps from the Sun",
                url = "https://www.youtube.com/embed/7NykS2kv_k8?playlist=7NykS2kv_k8&loop=1;rel=0&autoplay=1",
                copyright = "Dummy Copyright",
                thumbnailUrl = "https://img.youtube.com/vi/7NykS2kv_k8/0.jpg"
            ),

            // Skip 09 Intentionally

            GetPictureResponse(
                date = "$requestYearMonth-10",
                explanation = "Have you heard about the Hubble Ultra-Deep Field?  Either way, you've likely not " +
                    "heard about it like this -- please run your cursor over the featured image and listen!  " +
                    "The Hubble Ultra-Deep Field (HUDF) was created in 2003-2004 with the Hubble Space Telescope " +
                    "staring for a long time toward near-empty space so that distant, faint galaxies would become " +
                    "visible.  One of the most famous images in astronomy, the HUDF is featured here in a vibrant " +
                    "way -- with sonified distances. Pointing to a galaxy will play a note that indicates its " +
                    "approximate redshift. Because redshifts shift light toward the red end of the spectrum of " +
                    "light, they are depicted here by a shift of tone toward the low end of the spectrum of " +
                    "sound.  The further the galaxy, the greater its cosmological redshift (even if it appears " +
                    "blue), and the lower the tone that will be played. The average galaxy in the HUDF is about " +
                    "10.6 billion light years away and sounds like an F#. What's the most distant galaxy you can " +
                    "find?   Note: Sounds will only play on some browsers.  This week at NASA: Hubble #DeepFieldWeek",
                hdUrl = null,
                mediaType = "video",
                serviceVersion = "v1",
                title = "The Hubble Ultra Deep Field in Light and Sound",
                url = "https://apod.nasa.gov/apod/image/1803/AstroSoM/hudf.html",
                copyright = "Dummy Copyright",
                thumbnailUrl = ""
            ),
            GetPictureResponse(
                date = "$requestYearMonth-11",
                explanation = "Lorem 11 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/ngc1333_RGB-c2.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "NGC 1333: Stellar Nursery in Perseus",
                url = "https://apod.nasa.gov/apod/image/2111/ngc1333_RGB-c21024.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-12",
                explanation = "Lorem 12 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/M33_PS1_CROP_INSIGHT2048.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "M33: The Triangulum Galaxy",
                url = "https://apod.nasa.gov/apod/image/2111/M33_PS1_CROP_INSIGHT1024.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-13",
                explanation = "Lorem 13 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/67P_211107.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "Rosetta's Comet in Gemini",
                url = "https://apod.nasa.gov/apod/image/2111/67P_211107_1067.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-14",
                explanation = "Lorem 14 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/astronomy101_hk_750.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "How to Identify that Light in the Sky",
                url = "https://apod.nasa.gov/apod/image/2111/astronomy101_hk_960.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-15",
                explanation = "Lorem 15 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/EtnaLightPillar_Tine_5100.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "Light Pillar over Volcanic Etna",
                url = "https://apod.nasa.gov/apod/image/2111/EtnaLightPillar_Tine_960.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-16",
                explanation = "Lorem 16 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/Geminids2020_WangJin_2322.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "Geminids from Gemini",
                url = "https://apod.nasa.gov/apod/image/2111/Geminids2020_WangJin_960.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-17",
                explanation = "Most galaxies have a single nucleus -- does this galaxy have four? The strange " +
                    "answer leads astronomers to conclude that the nucleus of the surrounding galaxy is not " +
                    "even visible in this image. The central cloverleaf is rather light emitted from a background " +
                    "quasar. The gravitational field of the visible foreground galaxy breaks light from this " +
                    "distant quasar into four distinct images. The quasar must be properly aligned behind the " +
                    "center of a massive galaxy for a mirage like this to be evident. The general effect is known " +
                    "as gravitational lensing, and this specific case is known as the Einstein Cross. Stranger " +
                    "still, the images of the Einstein Cross vary in relative brightness, enhanced occasionally " +
                    "by the additional gravitational microlensing effect of specific stars in the foreground galaxy.",
                hdUrl = "https://apod.nasa.gov/apod/image/2110/qso2237_wiyn_1024.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "The Einstein Cross Gravitational Lens",
                url = "https://apod.nasa.gov/apod/image/2110/qso2237_wiyn_1024.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-18",
                explanation = "Lorem 18 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/moonwalk1.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "Full Moonlight",
                url = "https://apod.nasa.gov/apod/image/2111/moonwalk1c1024.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-19",
                explanation = "The dream was to capture both the waterfall and the Milky Way together. " +
                    "Difficulties included finding a good camera location, artificially illuminating the " +
                    "waterfall and the surrounding valley effectively, capturing the entire scene with numerous " +
                    "foreground and background shots, worrying that fireflies would be too distracting, keeping " +
                    "the camera dry, and avoiding stepping on a poisonous snake. Behold the result -- captured " +
                    "after midnight in mid-July and digitally stitched into a wide-angle panorama. The waterfall is " +
                    "the picturesque Zhulian waterfall in the Luoxiao Mountains in eastern Hunan Province, China. " +
                    "The central band of our Milky Way Galaxy crosses the sky and shows numerous dark dust " +
                    "filaments and colorful nebulas. Bright stars dot the sky -- all residing in the nearby " +
                    "Milky Way -- including the Summer Triangle with bright Vega visible above the Milky Way's " +
                    "arch. After capturing all 78 component exposures for you to enjoy, the photographer and " +
                    "friends enjoyed the view themselves for the rest of the night. Discovery + Outreach: " +
                    "Graduate student research position open for APOD",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/MilkyWayWaterfall_XieJie_2500.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "A Waterfall and the Milky Way",
                url = "https://apod.nasa.gov/apod/image/2111/MilkyWayWaterfall_XieJie_960.jpg",
                copyright = "Xie Jie",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-20",
                explanation = "Lorem 20 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/IMG_8522-1.png",
                mediaType = "image",
                serviceVersion = "v1",
                title = "An Almost Total Lunar Eclipse",
                url = "https://apod.nasa.gov/apod/image/2111/IMG_8522-1_1024.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-21",
                explanation = "Lorem 21 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/CometLeonard_Bartlett_4006.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "Introducing Comet Leonard",
                url = "https://apod.nasa.gov/apod/image/2111/CometLeonard_Bartlett_960.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-22",
                explanation = "Lorem 22 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/LunarEclipseBuilding_Beletsky_1400.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "Lunar Eclipse over a Skyscraper",
                url = "https://apod.nasa.gov/apod/image/2111/LunarEclipseBuilding_Beletsky_960.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-23",
                explanation = "Lorem 23 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/ActiveSun_NuSTAR_1600.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "The Sun in X-rays from NuSTAR",
                url = "https://apod.nasa.gov/apod/image/2111/ActiveSun_NuSTAR_960.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-24",
                explanation = "Lorem 24 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/PleiadesB_Cannane_2419.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "Pleiades: The Seven Sisters Star Cluster",
                url = "https://apod.nasa.gov/apod/image/2111/PleiadesB_Cannane_960.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-25",
                explanation = "Lorem 25 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/Gout_EclipseCollage-small.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "At the Shadow's Edge",
                url = "https://apod.nasa.gov/apod/image/2111/Gout_EclipseCollage-1024.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-26",
                explanation = "Lorem 26 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/LH7528_36EclipsePartialWithPlieades_1280x1280.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "Great Refractor and Lunar Eclipse",
                url = "https://apod.nasa.gov/apod/image/2111/LH7528_36EclipsePartialWithPlieades_1024x1024.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-27",
                explanation = "Lorem 27 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/M101_hst6000.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "Messier 101",
                url = "https://apod.nasa.gov/apod/image/2111/M101_hst1280.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            ),
            GetPictureResponse(
                date = "$requestYearMonth-28",
                explanation = "Lorem 28 $mockText",
                hdUrl = "https://apod.nasa.gov/apod/image/2111/cometcliffs_RosettaAtkinson_960.jpg",
                mediaType = "image",
                serviceVersion = "v1",
                title = "A High Cliff on Comet Churyumov-Gerasimenko",
                url = "https://apod.nasa.gov/apod/image/2111/cometcliffs_RosettaAtkinson_960.jpg",
                copyright = "Dummy Copyright",
                thumbnailUrl = null
            )
        )

        if (daysInMonth > 28) {
            response.add(
                GetPictureResponse(
                    date = "$requestYearMonth-29",
                    explanation = "Lorem 29 $mockText",
                    hdUrl = "https://apod.nasa.gov/apod/image/2111/LLPegasi_HubbleLodge_1926.jpg",
                    mediaType = "image",
                    serviceVersion = "v1",
                    title = "The Extraordinary Spiral in LL Pegasi",
                    url = "https://apod.nasa.gov/apod/image/2111/LLPegasi_HubbleLodge_960.jpg",
                    copyright = "Dummy Copyright",
                    thumbnailUrl = null
                )
            )
        }

        if (daysInMonth > 29) {
            response.add(
                GetPictureResponse(
                    date = "$requestYearMonth-30",
                    explanation = "What's that moving across the sky? A planet just a bit too faint to see with " +
                        "the unaided eye: Uranus. The gas giant out past Saturn was tracked earlier this month " +
                        "near opposition -- when it was closest to Earth and at its brightest. The featured video " +
                        "captured by the Bayfordbury Observatory in Hertfordshire, UK is a four-hour time-lapse " +
                        "showing Uranus with its four largest moons in tow: Titania, Oberon, Umbriel and Ariel. " +
                        "Uranus' apparent motion past background stars is really dominated by Earth's own orbital " +
                        "motion around our Sun. \\t The cross seen centered on Uranus is called a diffraction " +
                        "spike and is caused by light diffracting around the four arms that hold one of the " +
                        "telescope's mirrors in place. The rotation of the diffraction spikes is not caused by " +
                        "the rotation of Uranus but, essentially, by the rotation of the Earth. During the next " +
                        "few months Uranus itself will be visible with binoculars, but, as always, to see its " +
                        "moons will require a telescope.",
                    hdUrl = null,
                    mediaType = "video",
                    serviceVersion = "v1",
                    title = "In Motion: Uranus and Moon",
                    url = "https://www.youtube.com/embed/VYWjxvm14Pk?rel=0",
                    copyright = "Dummy Copyright",
                    thumbnailUrl = "https://img.youtube.com/vi/VYWjxvm14Pk/0.jpg"
                )
            )
        }

        if (daysInMonth > 30) {
            response.add(
                GetPictureResponse(
                    date = "$requestYearMonth-31",
                    explanation = "Is our universe haunted?  It might look that way on this dark matter map.  " +
                        "The gravity of unseen dark matter is the leading explanation for why galaxies rotate so " +
                        "fast, why galaxies orbit clusters so fast, why gravitational lenses so strongly deflect " +
                        "light, and why visible matter is distributed as it is both in the local universe and on " +
                        "the cosmic microwave background.  The featured image from the American Museum of Natural " +
                        "History's Hayden Planetarium Space Show Dark Universe highlights one example of how " +
                        "pervasive dark matter might haunt our universe.  In this frame from a detailed computer " +
                        "simulation, complex filaments of dark matter, shown in black, are strewn about the " +
                        "universe like spider webs, while the relatively rare clumps of familiar baryonic " +
                        "matter are colored orange. These simulations are good statistical matches to astronomical " +
                        "observations.  In what is perhaps a scarier turn of events, dark matter -- " +
                        "although quite strange and in an unknown form -- is no longer thought to be the " +
                        "strangest source of gravity in the universe. That honor now falls to dark energy, " +
                        "a more uniform source of repulsive gravity that seems to now dominate the expansion " +
                        "of the entire universe.   Not only Halloween: Today is Dark Matter Day.",
                    hdUrl = "https://apod.nasa.gov/apod/image/2110/DarkMatter_KipacAmnh_1200.jpg",
                    mediaType = "image",
                    serviceVersion = "v1",
                    title = "Dark Matter in a Simulated Universe",
                    url = "https://apod.nasa.gov/apod/image/2110/DarkMatter_KipacAmnh_960.jpg",
                    copyright = "Tom AbelRalf KaehlerKIPACSLACAMNH",
                    thumbnailUrl = null
                )
            )
        }

        return response
    }
}
