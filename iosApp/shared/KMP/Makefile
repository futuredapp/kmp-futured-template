# Define the default target
all: build

# Define the build target
build:
	@echo "KMP_FRAMEWORK_BUILD_TYPE: $(KMP_FRAMEWORK_BUILD_TYPE)"
	@echo "KMP_BUILD_FLAVOR: $(KMP_BUILD_FLAVOR)"
ifeq ($(KMP_FRAMEWORK_BUILD_TYPE), debug)
	@echo "Building debug framework"
	cd ../../.. && \
	./gradlew assembleAndCopyDebugSwiftPackage --no-daemon -P buildkonfig.flavor=$(KMP_BUILD_FLAVOR)
else ifeq ($(KMP_FRAMEWORK_BUILD_TYPE), release)
	@echo "Building release framework"
	cd ../../.. && \
	./gradlew assembleAndCopyReleaseSwiftPackage --no-daemon -P buildkonfig.flavor=$(KMP_BUILD_FLAVOR)
else
	@echo "Unknown or no build type specified. Please provide 'KMP_FRAMEWORK_BUILD_TYPE' env variable: [debug|release]"
	exit 1
endif

# Define the clean target
clean:
	cd ../../.. && \
	./gradlew clean

.PHONY: all build clean
