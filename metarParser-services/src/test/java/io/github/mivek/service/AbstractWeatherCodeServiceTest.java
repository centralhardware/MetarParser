package io.github.mivek.service;

import io.github.mivek.exception.ErrorCodes;
import io.github.mivek.exception.ParseException;
import io.github.mivek.model.AbstractWeatherCode;
import io.github.mivek.model.Metar;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Disabled
abstract class AbstractWeatherCodeServiceTest<T extends AbstractWeatherCode> {
    protected abstract AbstractWeatherCodeService<T> getService();

    @Test
    void testRetrieveFromAirportInvalid() {
        ParseException e = assertThrows(ParseException.class, () -> getService().retrieveFromAirport("RandomIcao"));
        assertEquals(ErrorCodes.ERROR_CODE_INVALID_ICAO, e.getErrorCode());
    }

    @Test
    void testRetrieveFromAirport() throws IOException, ParseException, URISyntaxException, InterruptedException {
        T res = getService().retrieveFromAirport("LFPG");
        assertThat(res, notNullValue());
        assertThat(res.getAirport().getIcao(), is("LFPG"));
    }

    /**
     * <a href="https://github.com/mivek/MetarParser/issues/576">issue 576</a>
     */
    @Test
    void testAirportNotFound() throws IOException, ParseException, URISyntaxException, InterruptedException {
        T res = getService().retrieveFromAirport("lftm");
        assertThat(res, nullValue());
    }

}
