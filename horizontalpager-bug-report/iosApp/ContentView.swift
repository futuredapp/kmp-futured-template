import SwiftUI
import ComposeApp

struct ContentView: View {
    var body: some View {
        NavigationStack {
            HomeView()
        }
    }
}

struct HomeView: View {
    var body: some View {
        VStack(spacing: 16) {
            NavigationLink("CMP Pager Screen (broken gesture)") {
                PagerView()
            }
            .buttonStyle(.borderedProminent)
            NavigationLink("CMP Dummy Screen (working back gesture)") {
                DummyView()
            }
            .buttonStyle(.borderedProminent)
            NavigationLink("SwiftUI Pager (working back gesture)") {
                SwiftUIPagerView()
            }
            .buttonStyle(.borderedProminent)
        }
        .navigationTitle("Home")
        .navigationBarTitleDisplayMode(.inline)
    }
}

struct PagerView: View {
    var body: some View {
        PagerComposeView()
            .navigationTitle("Pager")
            .navigationBarTitleDisplayMode(.inline)
    }
}

struct PagerComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.PagerViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
}

struct DummyView: View {
    var body: some View {
        DummyComposeView()
            .navigationTitle("Dummy")
            .navigationBarTitleDisplayMode(.inline)
    }
}

struct SwiftUIPagerView: View {
    let pageColors: [Color] = [.red, .green, .blue, .yellow, .purple]

    var body: some View {
        TabView {
            ForEach(Array(pageColors.enumerated()), id: \.offset) { index, color in
                color
                    .opacity(0.3)
                    .overlay(Text("Page \(index + 1)").font(.largeTitle))
            }
        }
        .tabViewStyle(.page)
        .indexViewStyle(.page(backgroundDisplayMode: .always))
        .navigationTitle("SwiftUI Pager")
        .navigationBarTitleDisplayMode(.inline)
    }
}

struct DummyComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.DummyViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
}
