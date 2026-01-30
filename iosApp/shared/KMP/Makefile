# Check build type
ifeq ($(KMP_FRAMEWORK_BUILD_TYPE), debug)
    BUILD_MODE := Debug
else ifeq ($(KMP_FRAMEWORK_BUILD_TYPE), release)
    BUILD_MODE := Release
else
    $(error KMP_FRAMEWORK_BUILD_TYPE must be 'debug' or 'release')
endif

# Task name: assembleAndCopy[Debug|Release]SwiftPackage
GRADLE_TASK := assembleAndCopy$(BUILD_MODE)SwiftPackage

# Initialize args with the flavor
GRADLE_ARGS := -Pbuildkonfig.flavor=$(KMP_BUILD_FLAVOR)

# Add isStatic
ifdef KMP_IS_STATIC
    GRADLE_ARGS += -PisStatic=$(KMP_IS_STATIC)
endif

all: build

build:
	@echo "------------------------------------------------"
	@echo "Config: $(KMP_FRAMEWORK_BUILD_TYPE) | Flavor: $(KMP_BUILD_FLAVOR)"
	@echo "Static: $(KMP_IS_STATIC)"
	@echo "Executing: ./gradlew $(GRADLE_TASK) $(GRADLE_ARGS)"
	@echo "------------------------------------------------"
	cd ../../.. && \
	./gradlew $(GRADLE_TASK) --no-daemon $(GRADLE_ARGS)

clean:
	cd ../../.. && \
	./gradlew clean

.PHONY: all build clean
