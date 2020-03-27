#Comments Microservice
## 1. Prerequisites 
- Install [Rust](https://www.rust-lang.org/tools/install).
## 2. Running the Microservice
### Option 1: Command Line
1. Run "`cargo run`" in the same directory as this README.md.
    - On Windows, run it in PowerShell or Command Prompt.
    - On MacOS, run it in the terminal.
    - On Linux, run it in the terminal.
    
### Option 2: CLion or IntelliJ IDEA
1. Download [CLion](https://www.jetbrains.com/clion/) or [IntelliJ IDEA](https://www.jetbrains.com/idea/).
2. Get the [Rust plugin](https://plugins.jetbrains.com/plugin/8182-rust) which works with either IDE.
3. Open this directory in CLion or IntelliJ IDEA and click "Run".
  
## 3. Accessing the Microservice
The comments microservice is configured to run on port `50052`. It implements the gRPC services in `/proto/comments.proto`. [BloomRPC](https://github.com/uw-labs/bloomrpc) is recommended to test the services in isolation.