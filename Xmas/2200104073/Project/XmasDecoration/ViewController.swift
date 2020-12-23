//
//  ViewController.swift
//  XmasDecoration
//
//  Created by Naoki on 2020/12/23.
//

import UIKit

fileprivate struct Const {
    static let ornamentViewTag = 50
    static let trashViewViewTag = 100
    static let itemsTag = 1000
    
    static let ornamentSize = 80
    static let itemsViewMargin = 20
}


class ViewController: UIViewController {
    @IBOutlet weak var SelectionView: UIView!
    @IBOutlet weak var trashView: UIImageView!
    
    private var selectItems = SelectItems()
    private var selectedOrnamentView: UIImageView? = nil
    override func viewDidLoad() {
        super.viewDidLoad()
        setSelectItems()
        trashView.tag = Const.trashViewViewTag
        trashView.isUserInteractionEnabled = true
    }
    
    // タップ時のイベント
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        super.touchesEnded(touches, with: event)
        for touch: UITouch in touches {
            deselectOrnament()
            let tag = touch.view!.tag
            if tag == Const.ornamentViewTag {
                selectOrnament(imageView: touch.view! as! UIImageView)
                if isInsideTrash(imageView: touch.view as! UIImageView) {
                    touch.view?.removeFromSuperview()
                }
                
            }
            if tag >= Const.itemsTag {
                let index = tag - Const.itemsTag
                let imageView = UIImageView(image: selectItems.items[index])
                let frame = CGRect(x: 0, y: 0, width: Const.ornamentSize, height: Const.ornamentSize)
                let itemWidth = SelectionView.frame.width / CGFloat(selectItems.items.count)
                let centerY =  self.view.frame.height - SelectionView.frame.height - itemWidth / 2
                let centerX = (itemWidth / 2) + itemWidth * CGFloat(index)
                imageView.frame = frame
                imageView.center = CGPoint(x: centerX, y: centerY)
                imageView.tag = Const.ornamentViewTag
                imageView.isUserInteractionEnabled = true
                selectOrnament(imageView: imageView)
                self.view.addSubview(imageView)
            }
        }
    }
    
    // ドラッグイベント
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        super.touchesMoved(touches, with: event)
        
        guard let imageView = self.selectedOrnamentView else {
            return
        }
        let touchEvent = touches.first!
        // ドラッグ前の座標
        let preDx = touchEvent.previousLocation(in: self.view).x
        let preDy = touchEvent.previousLocation(in: self.view).y
        // ドラッグ後の座標
        let newDx = touchEvent.location(in: self.view).x
        let newDy = touchEvent.location(in: self.view).y
        // 移動距離
        let dx = newDx - preDx
        let dy = newDy - preDy
        
        var frame = imageView.frame
        frame.origin.x += dx
        frame.origin.y += dy
        imageView.frame = frame
    }
    
    // オーナメント選択View の setting
    private func setSelectItems() {
        let itemWidth = SelectionView.frame.width / CGFloat(selectItems.items.count)
        let imageWidth = itemWidth - CGFloat(Const.itemsViewMargin)
        let centerY = SelectionView.frame.height / 2
        
        for i in 0..<selectItems.items.count {
            let centerX = (itemWidth / 2) + itemWidth * CGFloat(i)
            let frame = CGRect(x: 0, y: 0, width: imageWidth, height: imageWidth)
            let imageView = UIImageView(image: selectItems.items[i])
            imageView.frame = frame
            imageView.center = CGPoint(x: centerX, y: centerY)
            imageView.tag = i + Const.itemsTag
            imageView.isUserInteractionEnabled = true
            SelectionView.addSubview(imageView)
        }
    }
    
    // オーナメント選択
    private func selectOrnament(imageView: UIImageView) {
        imageView.layer.borderColor = CGColor.init(red: 0, green: 0, blue: 100, alpha: 1)
        imageView.layer.borderWidth = 5
        self.selectedOrnamentView = imageView
    }
    
    // オーナメント選択解除
    private func deselectOrnament() {
        guard let imageView = self.selectedOrnamentView else {
            return
        }
        imageView.layer.borderWidth = 0
        self.selectedOrnamentView = nil
    }
    
    // ゴミ箱アイコンの当たり判定
    private func isInsideTrash(imageView: UIImageView) -> Bool {
        let minX = trashView.center.x - trashView.bounds.width / 2
        let maxX = trashView.center.x + trashView.bounds.width / 2
        let minY = trashView.center.y - trashView.bounds.height / 2
        let maxY = trashView.center.y + trashView.bounds.height / 2
        if minX < imageView.center.x && imageView.center.x < maxX {
            if minY < imageView.center.y && imageView.center.y < maxY {
                return true
            }
        }
        return false
    }
}
