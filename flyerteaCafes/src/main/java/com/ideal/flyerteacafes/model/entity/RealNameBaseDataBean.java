package com.ideal.flyerteacafes.model.entity;

import java.util.List;

/**
 * Created by fly on 2017/5/25.
 */

public class RealNameBaseDataBean {


    private List<Areas> areas;
    private List<Career> career;
    private List<String> interest;
    private List<String> position;

    public List<Areas> getAreas() {
        return areas;
    }

    public List<Career> getCareer() {
        return career;
    }

    public List<String> getInterest() {
        return interest;
    }

    public List<String> getPosition() {
        return position;
    }

    public static class Areas {
        private String id;
        private String name;
        private String level;
        private String upid;
        private String usetype;
        private String displayorder;
        private List<Citys> citys;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getLevel() {
            return level;
        }

        public String getUpid() {
            return upid;
        }

        public String getUsetype() {
            return usetype;
        }

        public String getDisplayorder() {
            return displayorder;
        }

        public List<Citys> getCitys() {
            return citys;
        }
    }


    public static class Citys {
        private String displayorder;
        private String id;
        private String level;
        private String name;
        private String upid;
        private String usetype;
        private List<Districts> districts;




        public String getDisplayorder() {
            return displayorder;
        }

        public String getId() {
            return id;
        }

        public String getLevel() {
            return level;
        }

        public String getName() {
            return name;
        }

        public String getUpid() {
            return upid;
        }

        public String getUsetype() {
            return usetype;
        }

        public List<Districts> getDistricts() {
            return districts;
        }
    }

    public static class Districts {

        private String displayorder;
        private String id;
        private String level;
        private String name;
        private String upid;
        private String usetype;
//        private List<Towns> towns;

        public String getDisplayorder() {
            return displayorder;
        }

        public String getId() {
            return id;
        }

        public String getLevel() {
            return level;
        }

        public String getName() {
            return name;
        }

        public String getUpid() {
            return upid;
        }

        public String getUsetype() {
            return usetype;
        }

//        public List<Towns> getTowns() {
//            return towns;
//        }
    }

    public static class Towns {
        private String displayorder;
        private String id;
        private String level;
        private String name;
        private String upid;
        private String usetype;

        public String getDisplayorder() {
            return displayorder;
        }

        public String getId() {
            return id;
        }

        public String getLevel() {
            return level;
        }

        public String getName() {
            return name;
        }

        public String getUpid() {
            return upid;
        }

        public String getUsetype() {
            return usetype;
        }
    }

    public static class Career {
        private String fid;
        private String id;
        private String name;
        private String status;
        private List<Career> subs;

        public String getFid() {
            return fid;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getStatus() {
            return status;
        }

        public List<Career> getSubs() {
            return subs;
        }
    }


}
