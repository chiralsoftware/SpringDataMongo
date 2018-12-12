package chiralsoftware.mongoaggregation.buckets;

import java.util.Date;

public class ImageCapture {
    
    private Date date;
    private String[] lbls;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String[] getLbls() {
        return lbls;
    }

    public void setLbls(String[] lbls) {
        this.lbls = lbls;
    }
    
    
}
