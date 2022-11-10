import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greeting()
    
    @StateObject var greetingInfo = GreetingInfo()


	var body: some View {
        VStack {
            Text("\(greet)")
            Text("Repo number: \(greetingInfo.number)")
            
        }
            .onAppear {
                greetingInfo.start()
            }
	}
}

class GreetingInfo: ObservableObject {
    @Published var number: Int = -1
    
    let viewModel = GreetingViewModel()
    
    func start() {
//        viewModel.state.watch { state in
//            self.number = state as! Int
//        }
        GreetingRepo().numbersCommonFlow().watch { number in
            self.number = number as! Int
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
