import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

//import kotlinx.coroutines.flow.*
//import net.claztec.sample.util.Resource
//
//inline fun <ResultType, RequestType> networkBoundResource(
//    crossinline query: () -> Flow<ResultType>,
//    crossinline fetch: suspend () -> RequestType,
//    crossinline saveFetchResult: suspend (RequestType) -> Unit,
//    crossinline shouldFetch: (ResultType) -> Boolean = { true }
//) = flow {
//    val data = query().first()
//
//    val flow = if (shouldFetch(data)) {
//        emit(Resource.Loading(data))
//
//        try {
//            saveFetchResult(fetch())
//            query().map { Resource.Success(it) }
//        } catch (throwable: Throwable) {
//            query().map { Resource.Error(throwable, it) }
//        }
//    } else {
//        query().map { Resource.Success(it) }
//    }
//
//    emitAll(flow)
//}

// ResultType: Type for the Resource data.
// RequestType: Type for the API response.
abstract class NetworkBoundResource<ResultType, RequestType> {
    // Called to save the result of the API response into the database
    @WorkerThread
    protected abstract suspend fun saveCallResult(item: RequestType)

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    // Called to get the cached data from the database.
    @MainThread
    protected abstract suspend fun loadFromDb(): Flow<ResultType>

    // Called to create the API call.
//    @MainThread
//    protected abstract fun createCall(): Flow<ApiResponse<RequestType>>

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    protected open fun onFetchFailed() {}
}
