import SwiftUI
import ComposeApp
@main
struct iOSApp: App {

    init(){
        DiHelperKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}