package reddist.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class HitBase {
    private String x;
    private String y;
    private String r;
    private String type;
    private String result;
}
