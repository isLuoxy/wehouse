package cn.l99.wehouse.service;

import cn.l99.wehouse.pojo.SearchHistory;
import org.springframework.stereotype.Repository;

public interface ISearchHistoryService {

    void addSearchHistory(SearchHistory searchHistory);
}
