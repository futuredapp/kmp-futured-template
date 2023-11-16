//
//  HomeNavigationView.swift
//  iosApp
//
//  Created by Matej Semančík on 26.10.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared
import SwiftUI

struct HomeNavigationView: View {

    private let stack: SkieSwiftStateFlow<ChildStack<HomeDestination, HomeEntry>>
    private let actions: HomeNavigationActions

    init(_ component: HomeNavigation) {
        self.stack = component.stack
        self.actions = component.actions
    }

    var body: some View {
        DecomposeNavigationStack(
            kotlinStack: stack,
            setPath: actions.iosPopTo
        ) { entry in
            switch onEnum(of: entry) {
            case .first(let entry):
                FirstView(entry.screen)
            case .second(let entry):
                SecondView(entry.screen)
            case .third(let entry):
                ThirdView(entry.screen)
            }
        }
    }
}
