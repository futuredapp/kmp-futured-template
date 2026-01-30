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

# Add build mode (simulator/device/all)
ifdef KMP_BUILD_MODE
    GRADLE_ARGS += -PkmpBuildMode=$(KMP_BUILD_MODE)
endif

# Add isStatic
ifdef KMP_IS_STATIC
    GRADLE_ARGS += -PisStatic=$(KMP_IS_STATIC)
endif

all: build

build:
	@echo "------------------------------------------------"
	@echo "Config: $(KMP_FRAMEWORK_BUILD_TYPE) | Flavor: $(KMP_BUILD_FLAVOR) | Mode: $(KMP_BUILD_MODE) | Static: $(KMP_IS_STATIC)"
	@echo "Executing: ./gradlew $(GRADLE_TASK) $(GRADLE_ARGS)"
	@echo "------------------------------------------------"
	cd ../../.. && \
	./gradlew $(GRADLE_TASK) --no-daemon $(GRADLE_ARGS)

clean:
	cd ../../.. && \
	./gradlew clean

.PHONY: all build clean
