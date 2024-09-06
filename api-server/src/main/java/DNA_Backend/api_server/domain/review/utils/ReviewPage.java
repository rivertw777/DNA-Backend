package DNA_Backend.api_server.domain.review.utils;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
public class ReviewPage<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;

    public ReviewPage(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }

}
