package TourData.backend.domain.post.service;

import TourData.backend.domain.post.dto.PostDto.PostResponse;
import java.util.List;

public interface PostService {
    List<PostResponse> getAllPosts();
}
