package cn.l99.wehouse.dao;

import cn.l99.wehouse.pojo.SearchHistory;
import org.springframework.stereotype.Repository;

/**
 * 搜索历史持久层
 */
@Repository
public interface SearchHistoryDao {

    void addSearchHistory(SearchHistory searchHistory);
}
