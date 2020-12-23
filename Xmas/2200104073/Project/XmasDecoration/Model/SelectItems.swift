//
//  SelectItems.swift
//  XmasDecoration
//
//  Created by Naoki on 2020/12/23.
//

import Foundation
import UIKit

// 画像アイテム
class SelectItems: NSObject {
    public var items: [UIImage] = []
    
    override init() {
        items.append(UIImage(named: "bell")!)
        items.append(UIImage(named: "candy")!)
        items.append(UIImage(named: "christmas_star")!)
        items.append(UIImage(named: "honetsuki")!)
    }
}
