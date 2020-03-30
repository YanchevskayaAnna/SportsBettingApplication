package sportsbetting.service;

import sportsbetting.model.result.Result;

import java.util.List;

public interface IResultService {
    Result save(Result bet);
    List<Result> findAll();
}
