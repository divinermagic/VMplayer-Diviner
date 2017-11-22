package a.itcast.mobileplayer95.lyrics;

/**
 * Created by divinermagic on 2017/11/21.
 */

public class Lyric {

    int startPoint;

    String content;

    public Lyric(int startPoint, String content) {
        this.startPoint = startPoint;
        this.content = content;
    }

    public int getStartPoint() {
        return startPoint;
    }

    public String getContent() {
        return content;
    }

    public void setStartPoint(int startPoint) {
        this.startPoint = startPoint;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Lyric{" +
                "startPoint=" + startPoint +
                ", content='" + content + '\'' +
                '}';
    }
}
