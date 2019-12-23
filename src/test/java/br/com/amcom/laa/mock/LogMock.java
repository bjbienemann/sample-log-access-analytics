package br.com.amcom.laa.mock;

import br.com.amcom.laa.domain.Log;

import java.util.*;

public class LogMock {

    private static final int AMOUNT_USER = 10;

    private List<String> urls;
    private Map<String, Region> uuids;

    private Random random;

    public LogMock() {
        random = new Random();
        urls = createUrls();
        uuids = createUuids();
    }


    public Log createUrl1() {
        return newLog("/pets/exotic/cats/10",
                new Date(1037825323957L),
                "5b019db5-b3d0-46d2-9963-437860af707f",
                Region.US_EAST_1.getCode());
    }

    public Log createUrl2() {
        return newLog("/pets/guaipeca/dogs/1",
                new Date(1037825323957L),
                "5b019db5-b3d0-46d2-9963-437860af707g",
                Region.US_WEST_2.getCode());
    }

    public Log createUrl3() {
        return newLog("/tiggers/bid/now",
                new Date(1037825323957L),
                "5b019db5-b3d0-46d2-9963-437860af707e",
                Region.AP_SOUTH_1.getCode());
    }

    public Log createUrl() {
        int i = random.nextInt(urls.size());
        String url = urls.get(i);

        i = random.nextInt(uuids.size());
        Object[] keys = uuids.keySet().toArray();
        String uuid = keys[i].toString();
        Region region = uuids.get(keys[i]);

        return newLog(url, uuid, region.getCode());
    }

    private Log newLog(String url, String Uuid, Integer regionCode) {
        return newLog(url, new Date(), Uuid, regionCode);
    }

    private Log newLog(String url, Date dateTime, String Uuid, Integer regionCode) {
        Log log = new Log();
        log.setUrl(url);
        log.setDateTime(dateTime);
        log.setUuid(Uuid);
        log.setRegionCode(regionCode);

        return log;
    }

    private List<String> createUrls() {
        return Arrays.asList(
                "/pets/exotic/cats/10",
                "/pets/exotic/cats/8",
                "/pets/exotic/cats/5",
                "/pets/exotic/cats/4",
                "/pets/guaipeca/dogs/1",
                "/pets/guaipeca/dogs/2",
                "/pets/guaipeca/dogs/10",
                "/pets/guaipeca/dogs/7",
                "/pets/guaipeca/dogs/6",
                "/tiggers/bid/now");
    }

    private Map<String, Region> createUuids() {
        Map<String, Region> uuids = new LinkedHashMap<>();
        for (int i = 0; i < AMOUNT_USER; i++) {
            String uuid = UUID.randomUUID().toString();
            int iRegion = random.nextInt(Region.values().length);
            Region region = Region.values()[iRegion];
            uuids.put(uuid, region);
        }

        return uuids;
    }

    public enum Region {
        US_EAST_1(1),
        US_WEST_2(2),
        AP_SOUTH_1(3);

        private Integer code;

        Region(Integer code){
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }
}
