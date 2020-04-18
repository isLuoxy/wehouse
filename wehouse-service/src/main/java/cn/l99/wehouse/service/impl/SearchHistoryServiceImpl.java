package cn.l99.wehouse.service.impl;

import cn.l99.wehouse.dao.SearchHistoryDao;
import cn.l99.wehouse.pojo.SearchHistory;
import cn.l99.wehouse.service.ISearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SearchHistoryServiceImpl implements ISearchHistoryService {

    final SearchHistoryDao searchHistoryDao;

    @Autowired
    public SearchHistoryServiceImpl(SearchHistoryDao searchHistoryDao) {
        this.searchHistoryDao = searchHistoryDao;
    }

    @Override
    @Async
    public void addSearchHistory(SearchHistory searchHistory) {
        searchHistoryDao.addSearchHistory(searchHistory);
    }
}
