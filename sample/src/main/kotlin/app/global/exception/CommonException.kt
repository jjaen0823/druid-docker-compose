package app.global.exception

class CommonException {
    class BadRequestParamException(message: String) : RuntimeException(message)
    class BadRequestHeaderException(message: String) : RuntimeException(message)
    class UnAuthorizedException(message: String) : RuntimeException(message)
    class DataNotFoundException(message: String) : RuntimeException(message)
    class InternalServerException(message: String) : RuntimeException(message)
    class ResourceNotFoundException(message: String) : RuntimeException(message)
    class ClientUnavailableException(message: String) : RuntimeException(message)
}