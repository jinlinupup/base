package cn.gankao.bbbcheck.draw;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PageInfo
 * @Description TODO
 * @Author jinlin
 * @Date 2021/11/4 17:39
 * @Version 1.0
 */
public class PageInfo implements Serializable {

    /**
     * my_getAipenPaperPageInfo : {"page":{"image_url":"https://zhinengbi.qiaoxuesi.com/aipen/pageimg/1634778707218/已转笔记本2-X_11.png","book":{"id":"ckv0912jedy0z0a092xbt1r4g","name":"已转笔记本2-X","page_count":20,"book_type":4},"mm_width":192,"px_height":3117,"bookPrtPageID":[{"pageType":"BBB","pageID":"1645","sizeType":"14"}],"areas":[{"x2":190.92,"hotSpots":[{"h_type":"Input","params2":null,"command":null,"x2":189.56,"id":"ckvryjt6h3whm0a75u9khq4li","y1":2.04,"y2":261.87,"x1":1.36,"referContent":null,"params3":null,"props":[],"params1":null}],"id":"ckvryjt6d3whl0a754kne2ob7","y1":0.34,"y2":262.89,"x1":0.34}],"px_width":2267,"mm_height":264,"page_number":11,"pageKey":"ckv0912kmdy1a0a09e2zwf672"},"hwr":[{"area":{"id":"ckvryjt6d3whl0a754kne2ob7"},"book":{"id":"ckv0912jedy0z0a092xbt1r4g"},"page":{"id":"ckv0912kmdy1a0a09e2zwf672"},"contents":[["41.25,100.88","41.16,101.00","41.14,101.02","41.10,101.05","41.10,101.05","41.08,101.07","41.08,101.07","41.08,101.07","41.10,101.05","41.12,101.05","41.14,101.05","41.16,101.11","41.38,101.53","0.00,0.00",{"color":"#000000","width":1.5,"sort":35}],["41.25,100.88","41.16,101.00","41.14,101.02","41.10,101.05","41.10,101.05","41.08,101.07","41.08,101.07","41.08,101.07","41.10,101.05","41.12,101.05","41.14,101.05","41.16,101.11","41.38,101.53","0.00,0.00",{"color":"#000000","width":1.5,"sort":35}],["47.68,104.50","47.60,104.43","47.58,104.37","47.58,104.31","47.58,104.26","47.60,104.24","47.60,104.24","47.60,104.24","47.64,104.24","47.66,104.22","47.68,104.24","47.73,104.24","47.77,104.22","47.81,104.24","47.87,104.26","47.98,104.31","48.11,104.37","48.19,104.39","48.36,104.45","48.47,104.50","48.70,104.60","48.91,104.71","49.17,104.86","49.42,105.00","49.61,105.15","49.86,105.28","50.10,105.51","50.31,105.68","50.50,105.87","50.65,106.02","50.82,106.21","51.01,106.42","51.24,106.61","51.47,106.82","51.68,107.06","51.90,107.23","52.11,107.46","52.34,107.69","52.64,107.90","52.87,108.14","53.12,108.37","53.34,108.56","53.61,108.75","53.86,108.98","54.16,109.26","54.41,109.47","54.69,109.70","55.03,110.02","55.28,110.25","55.54,110.42","55.85,110.61","56.28,110.78","56.62,110.93","56.95,111.12","57.31,111.31","57.65,111.44","58.06,111.52","58.50,111.59","58.97,111.59","59.47,111.54","59.98,111.50","60.55,111.46","61.12,111.40","61.63,111.38","62.23,111.27","62.82,111.14","63.37,111.02","64.02,110.80","64.51,110.66","65.15,110.49","65.74,110.32","66.27,110.06","66.84,109.85","67.33,109.62","67.79,109.36","68.17,109.13","68.49,108.94","68.66,108.81","68.87,108.62","68.94,108.52","69.10,108.31","69.25,108.09","69.32,107.97","69.36,107.88","69.38,107.84","69.40,107.76","69.44,107.65","69.46,107.61","69.46,107.56","69.46,107.59","69.49,107.56","69.49,107.54","69.44,107.50","69.42,107.50","69.10,107.50","0.00,0.00",{"color":"#000000","width":1.5,"sort":36}],["59.66,111.78","59.58,111.76","59.58,111.73","59.60,111.69","59.60,111.71","59.62,111.71","59.66,111.71","59.73,111.76","59.81,111.82","59.90,111.93","60.13,112.29","60.21,112.41","60.28,112.56","60.36,112.84","60.40,113.07","60.49,113.56","60.51,113.83","60.51,114.13","60.53,114.49","60.55,114.78","60.55,115.10","60.55,115.38","60.57,115.63","60.57,115.95","60.55,116.22","60.55,116.56","60.57,116.81","60.57,117.15","60.57,117.49","60.55,117.75","60.55,118.06","60.55,118.44","60.55,118.64","60.55,118.89","60.53,119.29","60.53,119.65","60.49,120.43","60.51,120.82","60.47,121.20","60.51,121.62","60.51,122.04","60.60,122.85","60.57,123.25","60.53,123.52","60.53,123.86","60.62,124.29","60.64,124.65","60.64,124.99","60.66,125.39","60.66,125.96","60.66,126.26","60.68,126.47","60.68,126.53","60.70,126.59","60.72,126.72","60.74,126.85","60.70,126.95","60.70,126.98","60.70,127.00","60.68,127.02","60.64,127.02","60.57,127.06","60.49,127.10","60.36,127.21","0.00,0.00",{"color":"#000000","width":1.5,"sort":37}],["55.71,118.32","55.26,118.17","55.16,118.17","55.11,118.19","55.11,118.23","55.11,118.21","55.30,118.28","55.60,118.28","55.92,118.32","56.32,118.30","56.68,118.30","57.46,118.34","57.89,118.34","58.25,118.32","58.58,118.32","58.92,118.34","59.20,118.36","59.56,118.38","59.66,118.36","59.75,118.36","59.81,118.38","59.85,118.40","59.90,118.42","59.90,118.47","59.94,118.49","59.96,118.51","59.96,118.51","59.98,118.53","59.96,118.59","60.00,118.76","59.98,118.89","59.90,119.10","59.83,119.31","59.79,119.50","59.73,119.78","59.62,120.07","59.52,120.29","59.37,120.60","59.20,121.01","58.99,121.43","58.75,121.77","58.48,122.11","58.25,122.45","57.97,122.83","57.72,123.25","57.46,123.57","57.19,123.82","56.83,124.24","56.45,124.69","55.98,125.05","55.49,125.39","55.01,125.73","54.52,126.00","54.05,126.30","53.59,126.51","53.21,126.59","52.47,126.70","52.13,126.68","51.85,126.59","0.00,0.00",{"color":"#000000","width":1.5,"sort":38}],["66.25,115.69","66.16,115.69","66.12,115.69","66.12,115.69","66.12,115.71","66.10,115.69","66.10,115.71","66.10,115.74","66.04,115.78","65.87,116.01","65.63,116.29","65.32,116.65","64.96,117.05","64.51,117.39","64.07,117.79","63.54,118.28","62.94,118.61","62.37,119.04","61.74,119.52","61.17,119.84","60.64,120.12","60.11,120.39","59.64,120.56","59.18,120.73","58.73,120.84","58.27,120.86","0.00,0.00",{"color":"#000000","width":1.5,"sort":39}],["60.91,120.27","60.91,120.27","60.98,120.31","61.08,120.39","61.19,120.46","61.34,120.50","61.57,120.60","61.82,120.77","62.16,120.98","62.50,121.22","62.90,121.39","63.28,121.66","63.69,121.94","64.02,122.23","64.38,122.49","64.77,122.70","65.23,122.89","65.65,123.10","66.10,123.33","66.52,123.48","67.03,123.69","67.50,123.82","68.00,123.95","68.53,124.03","69.06,124.10","69.63,124.08","70.23,124.14","70.78,124.16","71.41,124.20","72.09,124.18","72.83,124.08","73.66,123.97","74.50,123.80","75.41,123.57","76.17,123.23","0.00,0.00",{"color":"#000000","width":1.5,"sort":40}],["107.54,33.73","107.35,33.90","107.29,33.86","107.12,33.95","106.89,34.05","106.93,34.16","107.27,34.39","0.00,0.00",{"color":"#000000","width":1.5,"sort":41}],["107.54,33.73","107.35,33.90","107.29,33.86","107.12,33.95","106.89,34.05","106.93,34.16","107.27,34.39","0.00,0.00",{"color":"#000000","width":1.5,"sort":41}]],"id":"ckvrykjok3wij0a75jhc684o5","hotSpot":{"id":"ckvryjt6h3whm0a75u9khq4li"}}]}
     */

    private MyGetPagePointInfoBean my_getAipenPaperPageInfo;

    public MyGetPagePointInfoBean getMy_getAipenPaperPageInfo() {
        return my_getAipenPaperPageInfo;
    }

    public void setMy_getAipenPaperPageInfo(MyGetPagePointInfoBean my_getAipenPaperPageInfo) {
        this.my_getAipenPaperPageInfo = my_getAipenPaperPageInfo;
    }

    public static class MyGetPagePointInfoBean implements Serializable  {
        /**
         * page : {"image_url":"https://zhinengbi.qiaoxuesi.com/aipen/pageimg/1634778707218/已转笔记本2-X_11.png","book":{"id":"ckv0912jedy0z0a092xbt1r4g","name":"已转笔记本2-X","page_count":20,"book_type":4},"mm_width":192,"px_height":3117,"bookPrtPageID":[{"pageType":"BBB","pageID":"1645","sizeType":"14"}],"areas":[{"x2":190.92,"hotSpots":[{"h_type":"Input","params2":null,"command":null,"x2":189.56,"id":"ckvryjt6h3whm0a75u9khq4li","y1":2.04,"y2":261.87,"x1":1.36,"referContent":null,"params3":null,"props":[],"params1":null}],"id":"ckvryjt6d3whl0a754kne2ob7","y1":0.34,"y2":262.89,"x1":0.34}],"px_width":2267,"mm_height":264,"page_number":11,"pageKey":"ckv0912kmdy1a0a09e2zwf672"}
         * hwr : [{"area":{"id":"ckvryjt6d3whl0a754kne2ob7"},"book":{"id":"ckv0912jedy0z0a092xbt1r4g"},"page":{"id":"ckv0912kmdy1a0a09e2zwf672"},"contents":[["41.25,100.88","41.16,101.00","41.14,101.02","41.10,101.05","41.10,101.05","41.08,101.07","41.08,101.07","41.08,101.07","41.10,101.05","41.12,101.05","41.14,101.05","41.16,101.11","41.38,101.53","0.00,0.00",{"color":"#000000","width":1.5,"sort":35}],["41.25,100.88","41.16,101.00","41.14,101.02","41.10,101.05","41.10,101.05","41.08,101.07","41.08,101.07","41.08,101.07","41.10,101.05","41.12,101.05","41.14,101.05","41.16,101.11","41.38,101.53","0.00,0.00",{"color":"#000000","width":1.5,"sort":35}],["47.68,104.50","47.60,104.43","47.58,104.37","47.58,104.31","47.58,104.26","47.60,104.24","47.60,104.24","47.60,104.24","47.64,104.24","47.66,104.22","47.68,104.24","47.73,104.24","47.77,104.22","47.81,104.24","47.87,104.26","47.98,104.31","48.11,104.37","48.19,104.39","48.36,104.45","48.47,104.50","48.70,104.60","48.91,104.71","49.17,104.86","49.42,105.00","49.61,105.15","49.86,105.28","50.10,105.51","50.31,105.68","50.50,105.87","50.65,106.02","50.82,106.21","51.01,106.42","51.24,106.61","51.47,106.82","51.68,107.06","51.90,107.23","52.11,107.46","52.34,107.69","52.64,107.90","52.87,108.14","53.12,108.37","53.34,108.56","53.61,108.75","53.86,108.98","54.16,109.26","54.41,109.47","54.69,109.70","55.03,110.02","55.28,110.25","55.54,110.42","55.85,110.61","56.28,110.78","56.62,110.93","56.95,111.12","57.31,111.31","57.65,111.44","58.06,111.52","58.50,111.59","58.97,111.59","59.47,111.54","59.98,111.50","60.55,111.46","61.12,111.40","61.63,111.38","62.23,111.27","62.82,111.14","63.37,111.02","64.02,110.80","64.51,110.66","65.15,110.49","65.74,110.32","66.27,110.06","66.84,109.85","67.33,109.62","67.79,109.36","68.17,109.13","68.49,108.94","68.66,108.81","68.87,108.62","68.94,108.52","69.10,108.31","69.25,108.09","69.32,107.97","69.36,107.88","69.38,107.84","69.40,107.76","69.44,107.65","69.46,107.61","69.46,107.56","69.46,107.59","69.49,107.56","69.49,107.54","69.44,107.50","69.42,107.50","69.10,107.50","0.00,0.00",{"color":"#000000","width":1.5,"sort":36}],["59.66,111.78","59.58,111.76","59.58,111.73","59.60,111.69","59.60,111.71","59.62,111.71","59.66,111.71","59.73,111.76","59.81,111.82","59.90,111.93","60.13,112.29","60.21,112.41","60.28,112.56","60.36,112.84","60.40,113.07","60.49,113.56","60.51,113.83","60.51,114.13","60.53,114.49","60.55,114.78","60.55,115.10","60.55,115.38","60.57,115.63","60.57,115.95","60.55,116.22","60.55,116.56","60.57,116.81","60.57,117.15","60.57,117.49","60.55,117.75","60.55,118.06","60.55,118.44","60.55,118.64","60.55,118.89","60.53,119.29","60.53,119.65","60.49,120.43","60.51,120.82","60.47,121.20","60.51,121.62","60.51,122.04","60.60,122.85","60.57,123.25","60.53,123.52","60.53,123.86","60.62,124.29","60.64,124.65","60.64,124.99","60.66,125.39","60.66,125.96","60.66,126.26","60.68,126.47","60.68,126.53","60.70,126.59","60.72,126.72","60.74,126.85","60.70,126.95","60.70,126.98","60.70,127.00","60.68,127.02","60.64,127.02","60.57,127.06","60.49,127.10","60.36,127.21","0.00,0.00",{"color":"#000000","width":1.5,"sort":37}],["55.71,118.32","55.26,118.17","55.16,118.17","55.11,118.19","55.11,118.23","55.11,118.21","55.30,118.28","55.60,118.28","55.92,118.32","56.32,118.30","56.68,118.30","57.46,118.34","57.89,118.34","58.25,118.32","58.58,118.32","58.92,118.34","59.20,118.36","59.56,118.38","59.66,118.36","59.75,118.36","59.81,118.38","59.85,118.40","59.90,118.42","59.90,118.47","59.94,118.49","59.96,118.51","59.96,118.51","59.98,118.53","59.96,118.59","60.00,118.76","59.98,118.89","59.90,119.10","59.83,119.31","59.79,119.50","59.73,119.78","59.62,120.07","59.52,120.29","59.37,120.60","59.20,121.01","58.99,121.43","58.75,121.77","58.48,122.11","58.25,122.45","57.97,122.83","57.72,123.25","57.46,123.57","57.19,123.82","56.83,124.24","56.45,124.69","55.98,125.05","55.49,125.39","55.01,125.73","54.52,126.00","54.05,126.30","53.59,126.51","53.21,126.59","52.47,126.70","52.13,126.68","51.85,126.59","0.00,0.00",{"color":"#000000","width":1.5,"sort":38}],["66.25,115.69","66.16,115.69","66.12,115.69","66.12,115.69","66.12,115.71","66.10,115.69","66.10,115.71","66.10,115.74","66.04,115.78","65.87,116.01","65.63,116.29","65.32,116.65","64.96,117.05","64.51,117.39","64.07,117.79","63.54,118.28","62.94,118.61","62.37,119.04","61.74,119.52","61.17,119.84","60.64,120.12","60.11,120.39","59.64,120.56","59.18,120.73","58.73,120.84","58.27,120.86","0.00,0.00",{"color":"#000000","width":1.5,"sort":39}],["60.91,120.27","60.91,120.27","60.98,120.31","61.08,120.39","61.19,120.46","61.34,120.50","61.57,120.60","61.82,120.77","62.16,120.98","62.50,121.22","62.90,121.39","63.28,121.66","63.69,121.94","64.02,122.23","64.38,122.49","64.77,122.70","65.23,122.89","65.65,123.10","66.10,123.33","66.52,123.48","67.03,123.69","67.50,123.82","68.00,123.95","68.53,124.03","69.06,124.10","69.63,124.08","70.23,124.14","70.78,124.16","71.41,124.20","72.09,124.18","72.83,124.08","73.66,123.97","74.50,123.80","75.41,123.57","76.17,123.23","0.00,0.00",{"color":"#000000","width":1.5,"sort":40}],["107.54,33.73","107.35,33.90","107.29,33.86","107.12,33.95","106.89,34.05","106.93,34.16","107.27,34.39","0.00,0.00",{"color":"#000000","width":1.5,"sort":41}],["107.54,33.73","107.35,33.90","107.29,33.86","107.12,33.95","106.89,34.05","106.93,34.16","107.27,34.39","0.00,0.00",{"color":"#000000","width":1.5,"sort":41}]],"id":"ckvrykjok3wij0a75jhc684o5","hotSpot":{"id":"ckvryjt6h3whm0a75u9khq4li"}}]
         */

        private PageBean page;
        private List<HwrBean> hwr;
        private List<String> noSaveHwrList;
        /**
         * helplines : {"Xaxis":[182],"Yaxis":[258]}
         */

        private HelplinesBean helplines;

        public List<String> getNoSaveHwrList() {
            return noSaveHwrList;
        }

        public void setNoSaveHwrList(List<String> noSaveHwrList) {
            this.noSaveHwrList = noSaveHwrList;
        }

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<HwrBean> getHwr() {
            return hwr;
        }

        public void setHwr(List<HwrBean> hwr) {
            this.hwr = hwr;
        }

        public HelplinesBean getHelplines() {
            return helplines;
        }

        public void setHelplines(HelplinesBean helplines) {
            this.helplines = helplines;
        }

        public static class PageBean implements Serializable {
            /**
             * image_url : https://zhinengbi.qiaoxuesi.com/aipen/pageimg/1634778707218/已转笔记本2-X_11.png
             * book : {"id":"ckv0912jedy0z0a092xbt1r4g","name":"已转笔记本2-X","page_count":20,"book_type":4}
             * mm_width : 192
             * px_height : 3117
             * bookPrtPageID : [{"pageType":"BBB","pageID":"1645","sizeType":"14"}]
             * areas : [{"x2":190.92,"hotSpots":[{"h_type":"Input","params2":null,"command":null,"x2":189.56,"id":"ckvryjt6h3whm0a75u9khq4li","y1":2.04,"y2":261.87,"x1":1.36,"referContent":null,"params3":null,"props":[],"params1":null}],"id":"ckvryjt6d3whl0a754kne2ob7","y1":0.34,"y2":262.89,"x1":0.34}]
             * px_width : 2267
             * mm_height : 264
             * page_number : 11
             * pageKey : ckv0912kmdy1a0a09e2zwf672
             */

            private String image_url;
            private BookBean book;
            private int mm_width;
            private int px_height;
            private int px_width;
            private int mm_height;
            private int page_number;
            private String pageKey;
            private List<BookPrtPageIDBean> bookPrtPageID;
            private List<AreasBean> areas;
            /**
             * prePageKey : ckvw71wza004w0943axsl6fe4
             * nextPageKey : ckvw71wzc004y094345m1v7mx
             */

            private String prePageKey;
            private String nextPageKey;
            private String myAiPenPaperID;

            public String getMyAiPenPaperID() {
                return myAiPenPaperID;
            }

            public void setMyAiPenPaperID(String myAiPenPaperID) {
                this.myAiPenPaperID = myAiPenPaperID;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public BookBean getBook() {
                return book;
            }

            public void setBook(BookBean book) {
                this.book = book;
            }

            public int getMm_width() {
                return mm_width;
            }

            public void setMm_width(int mm_width) {
                this.mm_width = mm_width;
            }

            public int getPx_height() {
                return px_height;
            }

            public void setPx_height(int px_height) {
                this.px_height = px_height;
            }

            public int getPx_width() {
                return px_width;
            }

            public void setPx_width(int px_width) {
                this.px_width = px_width;
            }

            public int getMm_height() {
                return mm_height;
            }

            public void setMm_height(int mm_height) {
                this.mm_height = mm_height;
            }

            public int getPage_number() {
                return page_number;
            }

            public void setPage_number(int page_number) {
                this.page_number = page_number;
            }

            public String getPageKey() {
                return pageKey;
            }

            public void setPageKey(String pageKey) {
                this.pageKey = pageKey;
            }

            public List<BookPrtPageIDBean> getBookPrtPageID() {
                return bookPrtPageID;
            }

            public void setBookPrtPageID(List<BookPrtPageIDBean> bookPrtPageID) {
                this.bookPrtPageID = bookPrtPageID;
            }

            public List<AreasBean> getAreas() {
                return areas;
            }

            public void setAreas(List<AreasBean> areas) {
                this.areas = areas;
            }

            public String getPrePageKey() {
                return prePageKey;
            }

            public void setPrePageKey(String prePageKey) {
                this.prePageKey = prePageKey;
            }

            public String getNextPageKey() {
                return nextPageKey;
            }

            public void setNextPageKey(String nextPageKey) {
                this.nextPageKey = nextPageKey;
            }

            public static class BookBean implements Serializable {
                /**
                 * id : ckv0912jedy0z0a092xbt1r4g
                 * name : 已转笔记本2-X
                 * page_count : 20
                 * book_type : 4
                 */

                private String id;
                private String name;
                private int page_count;
                private int book_type;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getPage_count() {
                    return page_count;
                }

                public void setPage_count(int page_count) {
                    this.page_count = page_count;
                }

                public int getBook_type() {
                    return book_type;
                }

                public void setBook_type(int book_type) {
                    this.book_type = book_type;
                }
            }

            public static class BookPrtPageIDBean implements Serializable {
                /**
                 * pageType : BBB
                 * pageID : 1645
                 * sizeType : 14
                 */

                private String pageType;
                private String pageID;
                private String sizeType;

                public String getPageType() {
                    return pageType;
                }

                public void setPageType(String pageType) {
                    this.pageType = pageType;
                }

                public String getPageID() {
                    return pageID;
                }

                public void setPageID(String pageID) {
                    this.pageID = pageID;
                }

                public String getSizeType() {
                    return sizeType;
                }

                public void setSizeType(String sizeType) {
                    this.sizeType = sizeType;
                }
            }

            public static class AreasBean implements Serializable {
                /**
                 * x2 : 190.92
                 * hotSpots : [{"h_type":"Input","params2":null,"command":null,"x2":189.56,"id":"ckvryjt6h3whm0a75u9khq4li","y1":2.04,"y2":261.87,"x1":1.36,"referContent":null,"params3":null,"props":[],"params1":null}]
                 * id : ckvryjt6d3whl0a754kne2ob7
                 * y1 : 0.34
                 * y2 : 262.89
                 * x1 : 0.34
                 */

                private double x2;
                private String id;
                private double y1;
                private double y2;
                private double x1;
                private List<HotSpotsBean> hotSpots;
                private BussinessPropsBean bussinessProps;


                public double getX2() {
                    return x2;
                }

                public void setX2(double x2) {
                    this.x2 = x2;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public double getY1() {
                    return y1;
                }

                public void setY1(double y1) {
                    this.y1 = y1;
                }

                public double getY2() {
                    return y2;
                }

                public void setY2(double y2) {
                    this.y2 = y2;
                }

                public double getX1() {
                    return x1;
                }

                public void setX1(double x1) {
                    this.x1 = x1;
                }

                public List<HotSpotsBean> getHotSpots() {
                    return hotSpots;
                }

                public void setHotSpots(List<HotSpotsBean> hotSpots) {
                    this.hotSpots = hotSpots;
                }

                public BussinessPropsBean getBussinessProps() {
                    return bussinessProps;
                }

                public void setBussinessProps(BussinessPropsBean bussinessProps) {
                    this.bussinessProps = bussinessProps;
                }

                public static class HotSpotsBean implements Serializable {
                    /**
                     * h_type : Input
                     * params2 : null
                     * command : null
                     * x2 : 189.56
                     * id : ckvryjt6h3whm0a75u9khq4li
                     * y1 : 2.04
                     * y2 : 261.87
                     * x1 : 1.36
                     * referContent : null
                     * params3 : null
                     * props : []
                     * params1 : null
                     */

                    private String h_type;
                    private Object params2;
                    private Object mapClassField;
                    private Object command;
                    private double x2;
                    private String id;
                    private double y1;
                    private double y2;
                    private double x1;
                    private Object referContent;
                    private Object params3;
                    private Object params1;
                    private Object bussinessProps;

                    public Object getMapClassField() {
                        return mapClassField;
                    }

                    public void setMapClassField(Object mapClassField) {
                        this.mapClassField = mapClassField;
                    }

                    public String getH_type() {
                        return h_type;
                    }

                    public void setH_type(String h_type) {
                        this.h_type = h_type;
                    }

                    public Object getParams2() {
                        return params2;
                    }

                    public void setParams2(Object params2) {
                        this.params2 = params2;
                    }

                    public Object getCommand() {
                        return command;
                    }

                    public void setCommand(Object command) {
                        this.command = command;
                    }

                    public double getX2() {
                        return x2;
                    }

                    public void setX2(double x2) {
                        this.x2 = x2;
                    }

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public double getY1() {
                        return y1;
                    }

                    public void setY1(double y1) {
                        this.y1 = y1;
                    }

                    public double getY2() {
                        return y2;
                    }

                    public void setY2(double y2) {
                        this.y2 = y2;
                    }

                    public double getX1() {
                        return x1;
                    }

                    public void setX1(double x1) {
                        this.x1 = x1;
                    }

                    public Object getReferContent() {
                        return referContent;
                    }

                    public void setReferContent(Object referContent) {
                        this.referContent = referContent;
                    }

                    public Object getParams3() {
                        return params3;
                    }

                    public void setParams3(Object params3) {
                        this.params3 = params3;
                    }

                    public Object getParams1() {
                        return params1;
                    }

                    public void setParams1(Object params1) {
                        this.params1 = params1;
                    }

                    public Object getBussinessProps() {
                        return bussinessProps;
                    }

                    public void setBussinessProps(Object bussinessProps) {
                        this.bussinessProps = bussinessProps;
                    }

                    @Override
                    public String toString() {
                        return "HotSpotsBean{" +
                                "h_type='" + h_type + '\'' +
                                ", params2=" + params2 +
                                ", mapClassField=" + mapClassField +
                                ", command=" + command +
                                ", x2=" + x2 +
                                ", id='" + id + '\'' +
                                ", y1=" + y1 +
                                ", y2=" + y2 +
                                ", x1=" + x1 +
                                ", referContent=" + referContent +
                                ", params3=" + params3 +
                                ", params1=" + params1 +
                                ", bussinessProps=" + bussinessProps +
                                '}';
                    }
                }

            }
        }


        public static class HwrBean implements Serializable {
            /**
             * area : {"id":"ckvryjt6d3whl0a754kne2ob7"}
             * book : {"id":"ckv0912jedy0z0a092xbt1r4g"}
             * page : {"id":"ckv0912kmdy1a0a09e2zwf672"}
             * contents : [["41.25,100.88","41.16,101.00","41.14,101.02","41.10,101.05","41.10,101.05","41.08,101.07","41.08,101.07","41.08,101.07","41.10,101.05","41.12,101.05","41.14,101.05","41.16,101.11","41.38,101.53","0.00,0.00",{"color":"#000000","width":1.5,"sort":35}],["41.25,100.88","41.16,101.00","41.14,101.02","41.10,101.05","41.10,101.05","41.08,101.07","41.08,101.07","41.08,101.07","41.10,101.05","41.12,101.05","41.14,101.05","41.16,101.11","41.38,101.53","0.00,0.00",{"color":"#000000","width":1.5,"sort":35}],["47.68,104.50","47.60,104.43","47.58,104.37","47.58,104.31","47.58,104.26","47.60,104.24","47.60,104.24","47.60,104.24","47.64,104.24","47.66,104.22","47.68,104.24","47.73,104.24","47.77,104.22","47.81,104.24","47.87,104.26","47.98,104.31","48.11,104.37","48.19,104.39","48.36,104.45","48.47,104.50","48.70,104.60","48.91,104.71","49.17,104.86","49.42,105.00","49.61,105.15","49.86,105.28","50.10,105.51","50.31,105.68","50.50,105.87","50.65,106.02","50.82,106.21","51.01,106.42","51.24,106.61","51.47,106.82","51.68,107.06","51.90,107.23","52.11,107.46","52.34,107.69","52.64,107.90","52.87,108.14","53.12,108.37","53.34,108.56","53.61,108.75","53.86,108.98","54.16,109.26","54.41,109.47","54.69,109.70","55.03,110.02","55.28,110.25","55.54,110.42","55.85,110.61","56.28,110.78","56.62,110.93","56.95,111.12","57.31,111.31","57.65,111.44","58.06,111.52","58.50,111.59","58.97,111.59","59.47,111.54","59.98,111.50","60.55,111.46","61.12,111.40","61.63,111.38","62.23,111.27","62.82,111.14","63.37,111.02","64.02,110.80","64.51,110.66","65.15,110.49","65.74,110.32","66.27,110.06","66.84,109.85","67.33,109.62","67.79,109.36","68.17,109.13","68.49,108.94","68.66,108.81","68.87,108.62","68.94,108.52","69.10,108.31","69.25,108.09","69.32,107.97","69.36,107.88","69.38,107.84","69.40,107.76","69.44,107.65","69.46,107.61","69.46,107.56","69.46,107.59","69.49,107.56","69.49,107.54","69.44,107.50","69.42,107.50","69.10,107.50","0.00,0.00",{"color":"#000000","width":1.5,"sort":36}],["59.66,111.78","59.58,111.76","59.58,111.73","59.60,111.69","59.60,111.71","59.62,111.71","59.66,111.71","59.73,111.76","59.81,111.82","59.90,111.93","60.13,112.29","60.21,112.41","60.28,112.56","60.36,112.84","60.40,113.07","60.49,113.56","60.51,113.83","60.51,114.13","60.53,114.49","60.55,114.78","60.55,115.10","60.55,115.38","60.57,115.63","60.57,115.95","60.55,116.22","60.55,116.56","60.57,116.81","60.57,117.15","60.57,117.49","60.55,117.75","60.55,118.06","60.55,118.44","60.55,118.64","60.55,118.89","60.53,119.29","60.53,119.65","60.49,120.43","60.51,120.82","60.47,121.20","60.51,121.62","60.51,122.04","60.60,122.85","60.57,123.25","60.53,123.52","60.53,123.86","60.62,124.29","60.64,124.65","60.64,124.99","60.66,125.39","60.66,125.96","60.66,126.26","60.68,126.47","60.68,126.53","60.70,126.59","60.72,126.72","60.74,126.85","60.70,126.95","60.70,126.98","60.70,127.00","60.68,127.02","60.64,127.02","60.57,127.06","60.49,127.10","60.36,127.21","0.00,0.00",{"color":"#000000","width":1.5,"sort":37}],["55.71,118.32","55.26,118.17","55.16,118.17","55.11,118.19","55.11,118.23","55.11,118.21","55.30,118.28","55.60,118.28","55.92,118.32","56.32,118.30","56.68,118.30","57.46,118.34","57.89,118.34","58.25,118.32","58.58,118.32","58.92,118.34","59.20,118.36","59.56,118.38","59.66,118.36","59.75,118.36","59.81,118.38","59.85,118.40","59.90,118.42","59.90,118.47","59.94,118.49","59.96,118.51","59.96,118.51","59.98,118.53","59.96,118.59","60.00,118.76","59.98,118.89","59.90,119.10","59.83,119.31","59.79,119.50","59.73,119.78","59.62,120.07","59.52,120.29","59.37,120.60","59.20,121.01","58.99,121.43","58.75,121.77","58.48,122.11","58.25,122.45","57.97,122.83","57.72,123.25","57.46,123.57","57.19,123.82","56.83,124.24","56.45,124.69","55.98,125.05","55.49,125.39","55.01,125.73","54.52,126.00","54.05,126.30","53.59,126.51","53.21,126.59","52.47,126.70","52.13,126.68","51.85,126.59","0.00,0.00",{"color":"#000000","width":1.5,"sort":38}],["66.25,115.69","66.16,115.69","66.12,115.69","66.12,115.69","66.12,115.71","66.10,115.69","66.10,115.71","66.10,115.74","66.04,115.78","65.87,116.01","65.63,116.29","65.32,116.65","64.96,117.05","64.51,117.39","64.07,117.79","63.54,118.28","62.94,118.61","62.37,119.04","61.74,119.52","61.17,119.84","60.64,120.12","60.11,120.39","59.64,120.56","59.18,120.73","58.73,120.84","58.27,120.86","0.00,0.00",{"color":"#000000","width":1.5,"sort":39}],["60.91,120.27","60.91,120.27","60.98,120.31","61.08,120.39","61.19,120.46","61.34,120.50","61.57,120.60","61.82,120.77","62.16,120.98","62.50,121.22","62.90,121.39","63.28,121.66","63.69,121.94","64.02,122.23","64.38,122.49","64.77,122.70","65.23,122.89","65.65,123.10","66.10,123.33","66.52,123.48","67.03,123.69","67.50,123.82","68.00,123.95","68.53,124.03","69.06,124.10","69.63,124.08","70.23,124.14","70.78,124.16","71.41,124.20","72.09,124.18","72.83,124.08","73.66,123.97","74.50,123.80","75.41,123.57","76.17,123.23","0.00,0.00",{"color":"#000000","width":1.5,"sort":40}],["107.54,33.73","107.35,33.90","107.29,33.86","107.12,33.95","106.89,34.05","106.93,34.16","107.27,34.39","0.00,0.00",{"color":"#000000","width":1.5,"sort":41}],["107.54,33.73","107.35,33.90","107.29,33.86","107.12,33.95","106.89,34.05","106.93,34.16","107.27,34.39","0.00,0.00",{"color":"#000000","width":1.5,"sort":41}]]
             * id : ckvrykjok3wij0a75jhc684o5
             * hotSpot : {"id":"ckvryjt6h3whm0a75u9khq4li"}
             */

            private AreaBean area;
            private BookBeanX book;
            private PageBeanX page;
            private String id;
            private HotSpotBean hotSpot;
            private List<List<Object>> contents;

            public AreaBean getArea() {
                return area;
            }

            public void setArea(AreaBean area) {
                this.area = area;
            }

            public BookBeanX getBook() {
                return book;
            }

            public void setBook(BookBeanX book) {
                this.book = book;
            }

            public PageBeanX getPage() {
                return page;
            }

            public void setPage(PageBeanX page) {
                this.page = page;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public HotSpotBean getHotSpot() {
                return hotSpot;
            }

            public void setHotSpot(HotSpotBean hotSpot) {
                this.hotSpot = hotSpot;
            }

            public List<List<Object>> getContents() {
                return contents;
            }

            public void setContents(List<List<Object>> contents) {
                this.contents = contents;
            }


            public static class AreaBean implements Serializable {
                /**
                 * id : ckvryjt6d3whl0a754kne2ob7
                 */

                private String id;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }

            public static class BookBeanX implements Serializable {
                /**
                 * id : ckv0912jedy0z0a092xbt1r4g
                 */

                private String id;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }

            public static class PageBeanX implements Serializable {
                /**
                 * id : ckv0912kmdy1a0a09e2zwf672
                 */

                private String id;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }

            public static class HotSpotBean implements Serializable {
                /**
                 * id : ckvryjt6h3whm0a75u9khq4li
                 */

                private String id;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }

            public static class HelplinesBean implements Serializable {
                private List<Integer> Xaxis;
                private List<Integer> Yaxis;

                public List<Integer> getXaxis() {
                    return Xaxis;
                }

                public void setXaxis(List<Integer> Xaxis) {
                    this.Xaxis = Xaxis;
                }

                public List<Integer> getYaxis() {
                    return Yaxis;
                }

                public void setYaxis(List<Integer> Yaxis) {
                    this.Yaxis = Yaxis;
                }
            }
        }

        public static class HelplinesBean implements Serializable {
            private List<Integer> Xaxis;
            private List<Integer> Yaxis;

            public List<Integer> getXaxis() {
                return Xaxis;
            }

            public void setXaxis(List<Integer> Xaxis) {
                this.Xaxis = Xaxis;
            }

            public List<Integer> getYaxis() {
                return Yaxis;
            }

            public void setYaxis(List<Integer> Yaxis) {
                this.Yaxis = Yaxis;
            }
        }
    }
}