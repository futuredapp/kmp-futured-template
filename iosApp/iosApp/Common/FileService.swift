import Foundation
import SwiftUI
import UIKit

final class FileService {
    static let shared = FileService()
    private let fileManager = FileManager.default

    private init() {
    }

    func store(image: UIImage, id: String) {
        do {
            let documentsDir = try self.fileManager.url(
                for: .cachesDirectory,
                in: .userDomainMask,
                appropriateFor: nil,
                create: true
            )
            let imagesDir = documentsDir.appending(path: "images")
            try self.fileManager.createDirectory(at: imagesDir, withIntermediateDirectories: true, attributes: nil)

            let imageUrl = imagesDir.appending(path: "\(id).jpeg")
            guard let data = image.jpegData(compressionQuality: 1) else {
                throw Error.unexpected
            }
            try data.write(to: imageUrl)
        } catch {
            return
        }
    }

    func loadImage(id: String) -> UIImage? {
        do {
            let documentsDir = try self.fileManager.url(
                for: .cachesDirectory,
                in: .userDomainMask,
                appropriateFor: nil,
                create: false
            )
            let imageDir = documentsDir.appending(path: "images").appending(path: "\(id).jpeg")
            let data = try Data(contentsOf: imageDir)
            guard let uiimage = UIImage(data: data) else {
                throw Error.unexpected
            }
            return uiimage
        } catch {
            return nil
        }
    }
}

extension FileService {
    enum Error: Swift.Error {
        case unexpected
    }
}
